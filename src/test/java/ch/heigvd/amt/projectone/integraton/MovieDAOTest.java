package ch.heigvd.amt.projectone.integraton;

import ch.heigvd.amt.projectone.services.dao.MovieManagerLocal;
import org.arquillian.container.chameleon.deployment.api.DeploymentParameters;
import org.arquillian.container.chameleon.deployment.maven.MavenBuild;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;

import javax.ejb.EJB;

@RunWith(Arquillian.class)
@MavenBuild
@DeploymentParameters(testable = true)
public class MovieDAOTest {

    @EJB
    MovieManagerLocal movieManager;


}
