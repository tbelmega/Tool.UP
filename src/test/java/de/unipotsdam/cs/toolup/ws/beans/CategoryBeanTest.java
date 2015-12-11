package de.unipotsdam.cs.toolup.ws.beans;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static de.unipotsdam.cs.toolup.util.AssertionUtil.assertCollectionEquals;
import static org.testng.AssertJUnit.assertEquals;

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
}
