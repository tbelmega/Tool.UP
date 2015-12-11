package de.unipotsdam.cs.toolup.model;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static de.unipotsdam.cs.toolup.util.AssertionUtil.assertCollectionEquals;
import static org.testng.AssertJUnit.assertEquals;

public class CategoryTest {

    @Test
    public void testThatCategoryMayHaveSuperCategory() {
        //arrange
        Category cat = new Category(CATEGORY_TEST_ID_11, null, null, null);
        String superCategoryId = CATEGORY_TEST_ID_13;

        //act
        cat.setSuperCategory(superCategoryId);

        //assert
        assertEquals(superCategoryId, cat.getSuperCategory());
    }

    @Test
    public void testThatCategoryMayHaveSubCategories() {
        //arrange
        Category cat = new Category(CATEGORY_TEST_ID_13, null, null, null);
        Collection<String> subcategories = Arrays.asList(CATEGORY_TEST_ID_11, CATEGORY_TEST_ID_12);

        //act
        cat.addSubCategories(subcategories);

        //assert
        assertCollectionEquals(subcategories, cat.getSubCategories());
    }

}
