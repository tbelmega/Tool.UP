package de.unipotsdam.cs.toolup.ws.beans;

import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectFactory;
import de.unipotsdam.cs.toolup.model.Category;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.*;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static de.unipotsdam.cs.toolup.util.AssertionUtil.assertCollectionEquals;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import static  de.unipotsdam.cs.toolup.model.BusinessObject.*;

public class CategoryBeanTest {

    @Test
    public void testThatCategoryBeanHasSuperCategoryField() throws Exception {
        //arrange
        String expectedSuperCategoryId = CATEGORY_TEST_ID_17;

        //act
        CategoryBean bean = CategoryBean.getBean(CATEGORY_TEST_ID_11);

        //assert
        JSONObject superCategory = new JSONObject(bean.getSuperCategory());
        assertEquals(expectedSuperCategoryId, superCategory.getString(JSON_KEY_ID));
    }

    @Test
    public void testThatCategoryBeanHasSubCategoriesField() throws Exception {
        //arrange
        Collection<String> expectedSubcategoryIDs = Arrays.asList(CATEGORY_TEST_ID_11, CATEGORY_TEST_ID_12);

        //act
        CategoryBean bean = CategoryBean.getBean(CATEGORY_TEST_ID_17);

        //assert
        List<String> actualSubcategoryIDs = new LinkedList<>();
        for (String subCatRepresentation: bean.getSubCategories()) {
            JSONObject cat = new JSONObject(subCatRepresentation);
            actualSubcategoryIDs.add(cat.getString(JSON_KEY_ID));
        }

        assertCollectionEquals(expectedSubcategoryIDs, actualSubcategoryIDs);
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
