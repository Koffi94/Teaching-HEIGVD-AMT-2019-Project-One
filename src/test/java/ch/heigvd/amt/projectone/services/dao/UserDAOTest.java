package ch.heigvd.amt.projectone.services.dao;
import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.IUserDAO;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class UserDAOTest {

    @EJB
    IUserDAO userManager;

    // User parameter
    private static final String USERNAME = "testUser";
    private static final String USERPW = "testpw";

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateUser() throws DuplicateKeyException, SQLException {
        User user = userManager.createUser(USERNAME, USERPW);
        assertNotNull(user);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAUser() throws DuplicateKeyException, SQLException {
        User user = userManager.createUser(USERNAME, USERPW);

        User userByName = userManager.findUserByName(USERNAME);

        User userByID = userManager.getUser(user.getUserId());

        assertEquals(user, userByName);
        assertEquals(user, userByID);
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAUser() throws DuplicateKeyException, SQLException {
        User user = userManager.createUser(USERNAME, USERPW);

        userManager.deleteUser(user.getUserId());

        assertFalse(userManager.checkUser(USERNAME, USERPW));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAUser() throws DuplicateKeyException, SQLException{
        User user = userManager.createUser(USERNAME, USERPW);


        String newName = "newtestName";
        String newPasswd = "newTestpw";
        //int userID = user.getUserId();

        assertNotNull(user.getUserId());

        userManager.updateUser(user.getUserId(), newName, newPasswd);

        // TODO Find why update is not working
        assertTrue(BCrypt.checkpw(newPasswd, user.getPassword()));

        assertEquals(newName, user.getUsername());


    }
}
