package de.unipotsdam.cs.toolup.ws.resource;

import de.unipotsdam.cs.toolup.ws.beans.CategoryBean;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;

import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static de.unipotsdam.cs.toolup.util.AssertionUtil.assertCollectionEquals;

public class CategoryResourceTest {

    @Test
    public void testThatCategoryResourceGETWithOutIdReturnsAllCategories() throws Exception {
        //arrange
        Collection<CategoryBean> expectedCats = Arrays.asList(
                CategoryBean.getBean(CATEGORY_TEST_ID_11),
                CategoryBean.getBean(CATEGORY_TEST_ID_12),
                CategoryBean.getBean(CATEGORY_TEST_ID_13)
        );

        CategoryResource catRes = new CategoryResource();

        //act
        Collection<CategoryBean> cats = catRes.getAll();

        //assert
        assertCollectionEquals(expectedCats, cats);
    }

}