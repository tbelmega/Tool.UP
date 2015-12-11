package de.unipotsdam.cs.toolup.database;


import de.unipotsdam.cs.toolup.model.Category;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static de.unipotsdam.cs.toolup.util.AssertionUtil.assertCollectionEquals;
import static org.testng.AssertJUnit.assertEquals;

public class DatabaseControllerForCategoryTest extends AbstractDatabaseTest {

    @Test
    public void testThatSuperCategoryIsLoadedFromDB() throws Exception {
        //arrange

        //act
        Category cat = (Category) db.load(CATEGORY_TEST_ID_11);

        //assert
        assertEquals(CATEGORY_TEST_ID_13, cat.getSuperCategory());
    }

    @Test
    public void testThatSubCategoriesAreLoadedFromDB() throws Exception {
        //arrange
        Collection<String> expectedSubCategories = Arrays.asList(CATEGORY_TEST_ID_11, CATEGORY_TEST_ID_12);

        //act
        Category cat = (Category) db.load(CATEGORY_TEST_ID_13);

        //assert
        assertCollectionEquals(expectedSubCategories, cat.getSubCategories());
    }
}
