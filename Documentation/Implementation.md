# AMT Project One - Functionnalities and implementation

**Authors: Nathanaël Mizutani [NatMiz](https://github.com/NatMiz), Olivier Koffi [Koffi94](https://github.com/Koffi94)**

## Business model

This application allow users to log the movies they have seen. They can specify the cinema in which they have seen each one of them, along with the screening they attended and the room in which it took place.<br/>

## Functionnalities

* This application is only accessible to registered member. So each visitor has to go through a login page.

* On the home page the user can see a list of the screenings he entered in the application. He can click on them to get details.

* Each user can see, only the screenings that he has created but movies and cinemas are public.

* Each user can add/edit/delete screenings, movies or cinemas.

* A pagging system is implemented with a fixed value of 10 entries per page that can be change through the global variable `PAGE_SIZE` in the code.

## Implementation

**UML diagram**
![UML diagram](./img/SchemaUML.png)

**Mockup**
![web application mockup](./img/Mockup-ProjectOne.png)

![Schema Multi-tier Architecture](./img/MultiTieredArchi.png)
*Source: Olivier Liechti AMT 2019 Course*

This project is organised in several tiers. It's a MVC based model but in our case we have tiers *Presentation*, *Model* and *Controller*.

In order to keep the code easy to read, we used Lombok.

### Model Tier

This tier contains domain object classes *Cinema*, *Movie*, *Screening* and *User*.
You can find them in the **ch.heigvd.amt.projectone.model** package in the project.
These Java classes represent the database's tables.

#### Code

The domain objects are simply java classes with getter and builder. Here, for example, the user class:

```java
@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter

public class User {

    private int userId;
    private String username;
    private String password;
}
```


### Presentation Tier

This tier contains servlet classes.
You can find them in the **ch.heigvd.amt.projectone.presentation** package in the project.
Servlets are used to route requests and responses of the application.
They forward them on JSP pages when needed. JSP format the content and send it back. 
Servlets filters are also used to manage authentications and authorizations.

#### Code

We use a filter (*AuthorizationFilter*) to deal with the user's authorizations. This way each action of the user is screened for clearance before proceeding.
The user goes through the login servlet only to authanticate himself for the first time or when his session has expired.

For each domain object, except the user, we have a corresponding manager class. We manage the user with the `LoginServlet`, `LogoutServlet` and `RegisterServlet`.

For example a `doGet` function for the ManageCinemaServlet:

```java
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cinemaId = Integer.parseInt(request.getParameter("cinema_id"));
        String operation = request.getParameter("operation_get");

        switch (operation) {
            case "detail" :
                Cinema cinema = cinemaManager.getCinema(cinemaId);
                request.setAttribute("cinema", cinema);
                request.getRequestDispatcher("./WEB-INF/pages/detailCinema.jsp").forward(request, response);
                break;
            case "delete" :
                cinemaManager.deleteCinema(cinemaId);
                response.sendRedirect("./home");
                break;
            default:
                break;
        }
    }
```

### Service Tier

This tier contains DAO classes.
You can find them in the **ch.heigvd.amt.projectone.services.dao** package in the project.
DAO classes are used to communicate with the MySQL database through the programming interface JDBC.

#### Code

All the DAO are package protected to insure that the tests will work.

We used Interfaces to allow multiple implementation of each DAO. Each interface contain CRUD methods and a getID() methods. Below is the ICinemaDAO interface:

```java
public interface ICinemaDAO {

    // CRUD
    public Cinema createCinema(String name, String city, String price);
    public Cinema findCinemaByName(String name);
    public Cinema getCinema(int cinemaId);
    public void updateCinema(int cinemaId, String name, String city, String price);
    public void deleteCinema(int cinemaId);
}
```
