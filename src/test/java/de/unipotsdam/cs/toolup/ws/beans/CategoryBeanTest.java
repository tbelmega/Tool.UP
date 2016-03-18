package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.model.BusinessObject;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static de.unipotsdam.cs.toolup.util.AssertionUtil.assertCollectionEquals;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import static  de.unipotsdam.cs.toolup.model.BusinessObject.*;

public class CategoryBeanTest {

    @Test
    public void testThatCategoryBeanHasSuperCategoryField() throws Exception {
        //arrange
        String expectedSuperCategoryId = CATEGORY_TEST_ID_13;

        //act
        CategoryBean bean = CategoryBean.getBean(CATEGORY_TEST_ID_11);

        //assert
        assertEquals(expectedSuperCategoryId, bean.getSuperCategory());
    }

    @Test
    public void testThatCategoryBeanHasSubCategoriesField() throws Exception {
        //arrange
        Collection<String> expectedSubcategoryIDs = Arrays.asList(CATEGORY_TEST_ID_11, CATEGORY_TEST_ID_12);

        //act
        CategoryBean bean = CategoryBean.getBean(CATEGORY_TEST_ID_13);

        //assert
        assertCollectionEquals(expectedSubcategoryIDs, bean.getSubCategories());
    }

    @Test
    public void testThatCategoryBeanContainsJSONRepresentationsOfRelatedBOs() throws Exception {
        //arrange
        CategoryBean bean = CategoryBean.getBean(CATEGORY_TEST_ID_11);
        Collection<String> relatedBOs = new HashSet<>();

        //act
        relatedBOs.addAll(bean.getApplications());
        relatedBOs.addAll(bean.getSubCategories());
        relatedBOs.add(bean.getSuperCategory());

        for (String jsonRepresentation: relatedBOs){
            JSONObject bo = new JSONObject(jsonRepresentation);

            //assert
            assertTrue(bo.has(JSON_KEY_TITLE));
        }
    }
}
