package ch.heigvd.amt.projectone.services.dao;

import ch.heigvd.amt.projectone.model.Movie;

import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class MovieDAOTest {

    @EJB
    MovieManagerLocal movieManager;

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAMovie(){
        movieManager.createMovie("movieTest", "1000", "test");
        // TODO assert equals
    }

    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void itShouldBePossibleToCreateAndRetrieveMovie(){
        // TODO
    }

}
