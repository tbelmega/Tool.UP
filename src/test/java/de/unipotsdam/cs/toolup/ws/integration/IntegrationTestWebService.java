package de.unipotsdam.cs.toolup.ws.integration;


import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static de.unipotsdam.cs.toolup.model.BusinessObject.*;
import static de.unipotsdam.cs.toolup.model.BusinessObjectTest.*;
import static junit.framework.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


public class IntegrationTestWebService extends AbstractTestWebService {

    private final static String APPLICATION_RESOURCE_URL = "/application/";
    private static final String CATEGORY_RESOURCE_URL = "/category/";
    private static final String FEATURE_RESOURCE_URL = "/feature/";

    @Test
    public void testThatGetRequestReturnsStatusCodeOK() throws Exception {
        //arrange
        HttpGet request = new HttpGet(TOOLUP_URL + APPLICATION_RESOURCE_URL + APPLICATION_TEST_ID_1);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testThatGetRequestReturnsMimeTypeJson() throws Exception {
        //arrange
        HttpGet request = new HttpGet(TOOLUP_URL + APPLICATION_RESOURCE_URL + APPLICATION_TEST_ID_1);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        assertEquals(ContentType.APPLICATION_JSON.getMimeType(), response.getHeaders(HttpHeaders.CONTENT_TYPE)[0].getValue());
    }

    /**
     * @param id the id of a BusinessObject of the type related to the resourceUrl
     */
    @Test(dataProvider = "provideIdAndResourceUrl")
    public void testThatGetRequestReturnsBusinessObjectAsJson(String id, String resourceUrl) throws Exception {
        //arrange
        BusinessObject expectedBO = DatabaseController.getInstance().load(id);

        HttpGet request = new HttpGet(TOOLUP_URL + resourceUrl + id);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        String responsePayload = EntityUtils.toString(response.getEntity());
        JSONObject actualBO = new JSONObject(responsePayload);
        assertEquals(expectedBO.getUuid(), actualBO.getString(JSON_KEY_ID));
        assertEquals(expectedBO.getTitle(), actualBO.getString(JSON_KEY_TITLE));
        assertEquals(expectedBO.getDescription(), actualBO.getString(JSON_KEY_DESCRIPTION));
    }

    @DataProvider
    public Iterator<Object[]> provideIdAndResourceUrl() {
        List<Object[]> data = Arrays.asList(
                new Object[]{CATEGORY_TEST_ID_11, CATEGORY_RESOURCE_URL},
                new Object[]{CATEGORY_TEST_ID_12, CATEGORY_RESOURCE_URL},
                new Object[]{APPLICATION_TEST_ID_1, APPLICATION_RESOURCE_URL},
                new Object[]{FEATURE_TEST_ID_22, FEATURE_RESOURCE_URL}
        );
        return data.iterator();
    }

    /**
     * @param expectedIds a Collection of all the IDs, that the test database has in the table related to the resource url
     */
    @Test(dataProvider = "provideAllIdsAndResourceUrl")
    public void testThatGetRequestReturnsAllApplicationsAsJson(Collection<String> expectedIds, String resourceUrl) throws Exception {
        //arrange
        HttpGet request = new HttpGet(TOOLUP_URL + resourceUrl);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        String responsePayload = EntityUtils.toString(response.getEntity());
        JSONArray allBOsOfThisTable = new JSONArray(responsePayload);
        JSONObject obj1 = allBOsOfThisTable.getJSONObject(0);
        JSONObject obj2 = allBOsOfThisTable.getJSONObject(1);

        assertTrue(expectedIds.contains(obj1.getString(JSON_KEY_ID)));
        assertTrue(expectedIds.contains(obj2.getString(JSON_KEY_ID)));
    }

    @DataProvider
    public Iterator<Object[]> provideAllIdsAndResourceUrl() {
        List<Object[]> data = Arrays.asList(
                new Object[]{Arrays.asList(CATEGORY_TEST_ID_11, CATEGORY_TEST_ID_12), CATEGORY_RESOURCE_URL},
                new Object[]{Arrays.asList(APPLICATION_TEST_ID_1, APPLICATION_TEST_ID_2), APPLICATION_RESOURCE_URL},
                new Object[]{Arrays.asList(FEATURE_TEST_ID_21, FEATURE_TEST_ID_22), FEATURE_RESOURCE_URL}
        );
        return data.iterator();
    }
}
