package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.UserManagerLocal;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import java.sql.SQLException;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class UserDAOTest {

    private static final String USERNAME = "testUser";
    private static final String USERPW = "testpw";

    @EJB
    UserManagerLocal userManager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateUser() throws DuplicateKeyException, SQLException {
        userManager.createUser(USERNAME, USERPW);
        assertNotNull(userManager.findUserByName(USERNAME));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAUser() throws DuplicateKeyException, SQLException {
        userManager.createUser(USERNAME, USERPW);

        User userByName = userManager.findUserByName(USERNAME);

        User userByID = userManager.getUser(userByName.getUserId());

        assertEquals(userByName, userByID);
        assertEquals(USERNAME, userByName.getUsername());
        assertEquals(USERNAME, userByID.getUsername());
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToDeleteAUser() throws DuplicateKeyException, SQLException {
        userManager.createUser(USERNAME, USERPW);

        userManager.deleteUser(userManager.findUserByName(USERNAME).getUserId());

        assertFalse(userManager.checkUser(USERNAME, USERPW));
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToUpdateAUser() throws DuplicateKeyException, SQLException{
        userManager.createUser(USERNAME, USERPW);

        String newName = "newtestName";
        String newPasswd = "newTestpw";

        int userID = userManager.findUserByName(USERNAME).getUserId();

        userManager.updateUser(userID, USERNAME, newPasswd);

        assertEquals(newPasswd, userManager.getUser(userID).getPassword());

        userManager.updateUser(userID, newName, USERPW);

        assertEquals(newName, userManager.getUser(userID).getUsername());


    }
}
