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
                CategoryBean.getBean(CATEGORY_TEST_ID_13),
                CategoryBean.getBean(CATEGORY_TEST_ID_14),
                CategoryBean.getBean(CATEGORY_TEST_ID_15),
                CategoryBean.getBean(CATEGORY_TEST_ID_16),
                CategoryBean.getBean(CATEGORY_TEST_ID_17)
        );

        //act
        Collection<CategoryBean> cats = (Collection<CategoryBean>) new CategoryResource().getAll().getEntity();

        //assert
        assertCollectionEquals(expectedCats, cats);
    }

    @Test
    public void testThatCategoryResourceReturnsAllCategoriesWithApplications() throws Exception {
        //arrange
        Collection<CategoryBean> expectedCats = Arrays.asList(
                CategoryBean.getBean(CATEGORY_TEST_ID_11),
                CategoryBean.getBean(CATEGORY_TEST_ID_12),
                CategoryBean.getBean(CATEGORY_TEST_ID_17)
        );

        //act
        Collection<CategoryBean> cats = (Collection<CategoryBean>) new CategoryResource().getAllWithApplication().getEntity();

        //assert
        assertCollectionEquals(expectedCats, cats);
    }

    @Test
    public void testThatCategoryResourceReturnsTopLevelCategories() throws Exception {
        //arrange
        Collection<CategoryBean> expectedCats = Arrays.asList(
                CategoryBean.getBean(CATEGORY_TEST_ID_13),
                CategoryBean.getBean(CATEGORY_TEST_ID_14),
                CategoryBean.getBean(CATEGORY_TEST_ID_15)
        );

        //act
        Collection<CategoryBean> cats = (Collection<CategoryBean>)new CategoryResource().getTopLevelCategories().getEntity();

        //assert
        assertCollectionEquals(expectedCats, cats);
    }

}
