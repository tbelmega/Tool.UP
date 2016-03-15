package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.util.AssertionUtil;
import de.unipotsdam.cs.toolup.ws.beans.FeatureBean;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.FEATURE_TEST_ID_21;
import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.FEATURE_TEST_ID_22;


public class FeatureResourceTest {

    @Test
    public void testThatFeatureResourceGETWithOutIdReturnsAllFeatures() throws Exception {
        //arrange
        Collection<FeatureBean> expectedFeats = Arrays.asList(
                FeatureBean.getBean(FEATURE_TEST_ID_21),
                FeatureBean.getBean(FEATURE_TEST_ID_22)
        );

        FeatureResource featRes = new FeatureResource();

        //act
        Collection<FeatureBean> feats = (Collection<FeatureBean>) featRes.getAll().getEntity();

        //assert
        AssertionUtil.assertCollectionEquals(expectedFeats, feats);
    }

}
