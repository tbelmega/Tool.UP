package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.ws.beans.ApplicationBean;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.APPLICATION_TEST_ID_1;
import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.APPLICATION_TEST_ID_2;
import static org.testng.Assert.assertEquals;

public class ApplicationResourceTest {

    @Test
    public void testThatApplicationResourceGETWithOutIdReturnsAllApplications() throws Exception {
        //arrange
        Collection<ApplicationBean> expectedApps = Arrays.asList(
                ApplicationBean.getBean(APPLICATION_TEST_ID_1),
                ApplicationBean.getBean(APPLICATION_TEST_ID_2)
        );

        ApplicationResource appRes = new ApplicationResource();

        //act
        Collection<ApplicationBean> apps = (Collection<ApplicationBean>) appRes.getAll().getEntity();

        //assert
        assertEquals(expectedApps, apps);
    }

}
