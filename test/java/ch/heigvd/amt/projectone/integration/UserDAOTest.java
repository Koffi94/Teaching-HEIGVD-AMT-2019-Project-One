package ch.heigvd.amt.projectone.integration;

import ch.heigvd.amt.projectone.model.User;
import ch.heigvd.amt.projectone.services.dao.UserManagerLocal;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.DuplicateKeyException;
import javax.ejb.EJB;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class UserDAOTest {

    @EJB
    UserManagerLocal userManager;

   /*@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }*/

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleTOCreateUser() throws DuplicateKeyException, SQLException {
        userManager.createUser("testUser", "testpw");
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveAUser() throws DuplicateKeyException, SQLException {
        userManager.createUser("testUser", "testpw");

        User userByName = userManager.findUserByName("testUser");

        User userByID = userManager.getUser(userByName.getUserId());

        assertEquals(userByName, userByID);
        assertEquals("testUser", userByName.getUsername());
        assertEquals("testUser", userByID.getUsername());
    }

    @Test
    @Transactional public void itShouldBePossibleToDeleteAUser() throws DuplicateKeyException, SQLException {

    }
}
