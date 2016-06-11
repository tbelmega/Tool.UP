package de.unipotsdam.cs.toolup.ws.resource;


import de.unipotsdam.cs.toolup.ws.beans.ApplicationBean;
import de.unipotsdam.cs.toolup.ws.beans.LookupResultBean;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static de.unipotsdam.cs.toolup.util.AssertionUtil.assertContainsAll;
import static org.testng.AssertJUnit.assertTrue;

@SuppressWarnings("ALL")
public class LookupResourceTest {


    @Test
    public void testThatPostRequestReturnsLookupResultBean() throws Exception {
        //arrange
        ApplicationBean app1 = ApplicationBean.getBean(APPLICATION_TEST_ID_1);
        ApplicationBean app2 = ApplicationBean.getBean(APPLICATION_TEST_ID_2);
        List<ApplicationBean> expectedApps = Arrays.asList(app1, app2);
        LookupResource lookupResource = new LookupResource();

        //act
        LookupResultBean apps = (LookupResultBean) lookupResource.post(FEATURE_TEST_ID_21).getEntity();

        //assert
        assertTrue(apps.getBestMatches().containsAll(expectedApps));
    }

    @Test
    public void testThatLookUpWithoutMatchingResultReturnsBestMatch() throws Exception {
        //arrange
        ApplicationBean app1 = ApplicationBean.getBean(APPLICATION_TEST_ID_1);
        List<ApplicationBean> expectedApps = Arrays.asList(app1);
        LookupResource lookupResource = new LookupResource();

        //act
        LookupResultBean apps = (LookupResultBean) lookupResource.post(FEATURE_TEST_ID_22 + ", " + FEATURE_TEST_ID_23).getEntity();

        //assert
        assertContainsAll(apps.getBestMatches(), expectedApps);
    }

    @Test
    public void testThatLookUpWithoutMatchingResultReturnsBestMatch2() throws Exception {
        //arrange
        ApplicationBean app1 = ApplicationBean.getBean(APPLICATION_TEST_ID_1);
        List<ApplicationBean> expectedApps = Arrays.asList(app1);
        LookupResource lookupResource = new LookupResource();

        //act
        LookupResultBean apps = (LookupResultBean) lookupResource.post(FEATURE_TEST_ID_21 + ", " + FEATURE_TEST_ID_22 + ", " + FEATURE_TEST_ID_23).getEntity();

        //assert
        assertContainsAll(apps.getBestMatches(), expectedApps);
    }

    @Test
    public void testThatLookUpWithoutMatchingResultReturnsBestMatch3() throws Exception {
        //arrange
        ApplicationBean app1 = ApplicationBean.getBean(APPLICATION_TEST_ID_1);
        ApplicationBean app2 = ApplicationBean.getBean(APPLICATION_TEST_ID_2);
        List<ApplicationBean> expectedApps = Arrays.asList(app1, app2);
        LookupResource lookupResource = new LookupResource();

        //act
        LookupResultBean apps = (LookupResultBean) lookupResource.post(FEATURE_TEST_ID_21 + ", " + FEATURE_TEST_ID_23).getEntity();

        //assert
        assertContainsAll(apps.getBestMatches(), expectedApps);
    }

    @Test
    public void testThatLookUpWithoutMatchingResultReturnsEmptyArray() throws Exception {
        //arrange

        List<ApplicationBean> expectedApps = new ArrayList<>();
        LookupResource lookupResource = new LookupResource();

        //act
        LookupResultBean apps = (LookupResultBean) lookupResource.post(FEATURE_TEST_ID_23).getEntity();

        //assert
        assertContainsAll(apps.getBestMatches(), expectedApps);
    }

}
