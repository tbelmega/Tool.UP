package de.unipotsdam.cs.toolup.ws.integration;


import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.Map;

import static de.unipotsdam.cs.toolup.model.BusinessObject.JSON_KEY_ID;
import static org.testng.AssertJUnit.assertTrue;

public class CategoryTestWebservice extends AbstractTestWebService {

    @Test
    public void testThatWebserviceReturnsAllCategoriesThatIncludeAnyApplication() throws Exception {
        //arrange
        Map<String, BusinessObject> expectedBOs = DatabaseController.getInstance().loadAllCategoriesWithApplication();

        HttpGet request = new HttpGet(TOOLUP_URL + "/category/withApplication");
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        String responsePayload = EntityUtils.toString(response.getEntity());
        JSONArray allBOsOfThisTable = new JSONArray(responsePayload);
        JSONObject firstBO = allBOsOfThisTable.getJSONObject(0);
        assertTrue(expectedBOs.containsKey(firstBO.getString(JSON_KEY_ID)));
    }

    @Test
    public void testThatWebserviceReturnsTopLevelCategories() throws Exception {
        //arrange
        Map<String, BusinessObject> expectedBOs = DatabaseController.getInstance().loadTopLevelCategories();

        HttpGet request = new HttpGet(TOOLUP_URL + "/category/toplevel");
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        String responsePayload = EntityUtils.toString(response.getEntity());
        JSONArray allBOsOfThisTable = new JSONArray(responsePayload);
        JSONObject firstBO = allBOsOfThisTable.getJSONObject(0);
        assertTrue(expectedBOs.containsKey(firstBO.getString(JSON_KEY_ID)));
    }

}
