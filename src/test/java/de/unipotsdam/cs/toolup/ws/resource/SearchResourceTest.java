package de.unipotsdam.cs.toolup.ws.resource;


import de.unipotsdam.cs.toolup.ws.beans.ApplicationBean;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static org.testng.AssertJUnit.assertTrue;

public class SearchResourceTest {

    @Test
    public void testThatSearchIncludesAppDescription() throws Exception {
        //arrange
        ApplicationBean expectedApp;
        SearchResource searchResource = new SearchResource();

        //act
        Collection<ApplicationBean> apps = (Collection<ApplicationBean>) searchResource.post("Description").getEntity();

        //assert
        expectedApp = ApplicationBean.getBean(APPLICATION_TEST_ID_1);
        assertTrue(apps.contains(expectedApp));
        expectedApp = ApplicationBean.getBean(APPLICATION_TEST_ID_2);
        assertTrue(apps.contains(expectedApp));
    }


}
