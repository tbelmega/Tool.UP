package de.unipotsdam.cs.toolup.ws.integration;


import de.unipotsdam.cs.toolup.database.DatabaseController;
import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectTest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static junit.framework.Assert.assertEquals;


public class IntegrationTestWebService extends AbstractTestWebService {

    private final static String APPLICATION_RESOURCE_URL = "/application/";
    private static final String CATEGORY_RESOURCE_URL = "/category/";
    private static final String FEATURE_RESOURCE_URL = "/feature/";

    @Test
    public void testThatGetRequestReturnsStatusCodeOK() throws Exception {
        //arrange
        HttpGet request = new HttpGet(TOOLUP_URL + APPLICATION_RESOURCE_URL + BusinessObjectTest.APPLICATION_TEST_ID_1);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testThatGetRequestReturnsMimeTypeJson() throws Exception {
        //arrange
        HttpGet request = new HttpGet(TOOLUP_URL + APPLICATION_RESOURCE_URL + BusinessObjectTest.APPLICATION_TEST_ID_1);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        assertEquals(ContentType.APPLICATION_JSON.getMimeType(), response.getHeaders(HttpHeaders.CONTENT_TYPE)[0].getValue());
    }

    @Test
    public void testThatGetRequestReturnsCategoryAsJson() throws Exception {
        //arrange
        BusinessObject expectedBO = DatabaseController.getInstance().load(BusinessObjectTest.CATEGORY_TEST_ID_11);

        HttpGet request = new HttpGet(TOOLUP_URL + CATEGORY_RESOURCE_URL + BusinessObjectTest.CATEGORY_TEST_ID_11);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        String responsePayload = EntityUtils.toString(response.getEntity());
        JSONObject actualBO = new JSONObject(responsePayload);
        assertEquals(expectedBO.getUuid(), actualBO.getString(BusinessObject.JSON_KEY_ID));
        assertEquals(expectedBO.getTitle(), actualBO.getString(BusinessObject.JSON_KEY_TITLE));
        assertEquals(expectedBO.getDescription(), actualBO.getString(BusinessObject.JSON_KEY_DESCRIPTION));
    }

    @Test
    public void testThatGetRequestReturnsFeatureAsJson() throws Exception {
        //arrange
        BusinessObject expectedBO = DatabaseController.getInstance().load( BusinessObjectTest.FEATURE_TEST_ID_21);

        HttpGet request = new HttpGet(TOOLUP_URL + FEATURE_RESOURCE_URL + BusinessObjectTest.FEATURE_TEST_ID_21);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        String responsePayload = EntityUtils.toString(response.getEntity());
        JSONObject actualBO = new JSONObject(responsePayload);
        assertEquals(expectedBO.getUuid(), actualBO.getString(BusinessObject.JSON_KEY_ID));
        assertEquals(expectedBO.getTitle(), actualBO.getString(BusinessObject.JSON_KEY_TITLE));
        assertEquals(expectedBO.getDescription(), actualBO.getString(BusinessObject.JSON_KEY_DESCRIPTION));
    }

    @Test
    public void testThatGetRequestReturnsApplicationAsJson() throws Exception {
        //arrange
        BusinessObject expectedBO = DatabaseController.getInstance().load(BusinessObjectTest.APPLICATION_TEST_ID_1);

        HttpGet request = new HttpGet(TOOLUP_URL + APPLICATION_RESOURCE_URL + BusinessObjectTest.APPLICATION_TEST_ID_1);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        //act
        HttpResponse response = client.execute(request);

        //assert
        String responsePayload = EntityUtils.toString(response.getEntity());
        JSONObject actualBO = new JSONObject(responsePayload);
        assertEquals(expectedBO.getUuid(), actualBO.getString(BusinessObject.JSON_KEY_ID));
        assertEquals(expectedBO.getTitle(), actualBO.getString(BusinessObject.JSON_KEY_TITLE));
        assertEquals(expectedBO.getDescription(), actualBO.getString(BusinessObject.JSON_KEY_DESCRIPTION));
    }
}
