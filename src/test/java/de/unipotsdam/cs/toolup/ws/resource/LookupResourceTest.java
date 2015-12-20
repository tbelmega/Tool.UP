package de.unipotsdam.cs.toolup.ws.resource;


import de.unipotsdam.cs.toolup.ws.beans.ApplicationBean;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static de.unipotsdam.cs.toolup.util.AssertionUtil.assertContainsAll;
import static org.testng.AssertJUnit.assertTrue;

@SuppressWarnings("ALL")
public class LookupResourceTest {

    @Test
    public void testThatPostRequestReturnsMapOfApplicationBeans() throws Exception {
        //arrange
        ApplicationBean app = ApplicationBean.getBean(APPLICATION_TEST_ID_1);
        LookupResource lookupResource = new LookupResource();

        //act
        Collection<ApplicationBean> apps = lookupResource.post(FEATURE_TEST_ID_21 + ", " + FEATURE_TEST_ID_22);

        //assert
        assertTrue(apps.contains(app));
    }

    @Test
    public void testThatPostRequestReturnsMapOfApplicationBeans2() throws Exception {
        //arrange
        ApplicationBean app1 = ApplicationBean.getBean(APPLICATION_TEST_ID_1);
        ApplicationBean app2 = ApplicationBean.getBean(APPLICATION_TEST_ID_2);
        List<ApplicationBean> expectedApps = Arrays.asList(app1, app2);
        LookupResource lookupResource = new LookupResource();

        //act
        Collection<ApplicationBean> apps = lookupResource.post(FEATURE_TEST_ID_21);

        //assert
        assertTrue(apps.containsAll(expectedApps));
    }

    @Test
    public void testThatLookUpWithoutMatchingResultReturnsBestMatch() throws Exception {
        //arrange
        ApplicationBean app1 = ApplicationBean.getBean(APPLICATION_TEST_ID_1);
        List<ApplicationBean> expectedApps = Arrays.asList(app1);
        LookupResource lookupResource = new LookupResource();

        //act
        Collection<ApplicationBean> apps = lookupResource.post(FEATURE_TEST_ID_22 + ", " + FEATURE_TEST_ID_23);

        //assert
        assertContainsAll(apps, expectedApps);
    }

    @Test
    public void testThatLookUpWithoutMatchingResultReturnsBestMatch2() throws Exception {
        //arrange
        ApplicationBean app1 = ApplicationBean.getBean(APPLICATION_TEST_ID_1);
        List<ApplicationBean> expectedApps = Arrays.asList(app1);
        LookupResource lookupResource = new LookupResource();

        //act
        Collection<ApplicationBean> apps = lookupResource.post(FEATURE_TEST_ID_21 + ", " + FEATURE_TEST_ID_22 + ", " + FEATURE_TEST_ID_23);

        //assert
        assertContainsAll(apps, expectedApps);
    }

    @Test
    public void testThatLookUpWithoutMatchingResultReturnsBestMatch3() throws Exception {
        //arrange
        ApplicationBean app1 = ApplicationBean.getBean(APPLICATION_TEST_ID_1);
        ApplicationBean app2 = ApplicationBean.getBean(APPLICATION_TEST_ID_2);
        List<ApplicationBean> expectedApps = Arrays.asList(app1, app2);
        LookupResource lookupResource = new LookupResource();

        //act
        Collection<ApplicationBean> apps = lookupResource.post(FEATURE_TEST_ID_21 + ", " + FEATURE_TEST_ID_23);

        //assert
        assertContainsAll(apps, expectedApps);
    }

    @Test
    public void testThatLookUpWithoutMatchingResultReturnsEmptyArray() throws Exception {
        //arrange

        List<ApplicationBean> expectedApps = new ArrayList<>();
        LookupResource lookupResource = new LookupResource();

        //act
        Collection<ApplicationBean> apps = lookupResource.post(FEATURE_TEST_ID_23);

        //assert
        assertContainsAll(apps, expectedApps);
    }

}
