package de.unipotsdam.cs.toolup.ws.integration;


import de.unipotsdam.cs.toolup.model.BusinessObject;
import de.unipotsdam.cs.toolup.model.BusinessObjectTest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

public class SearchTestWebservice extends AbstractTestWebservice {

    private final static String SEARCH_URL = "/search";

    @Test
    public void testThatSearchRequestReturnsStatusCodeOK() throws Exception {
        //arrange
        HttpPost request = new HttpPost(TOOLUP_URL + SEARCH_URL);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        ArrayList<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("search string", "description"));
        request.setEntity(new UrlEncodedFormEntity(postParameters));

        //act
        HttpResponse response = client.execute(request);

        //assert
       assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testThatLookupRequestReturnsExpectedApplication() throws Exception {
        //arrange
        HttpPost request = new HttpPost(TOOLUP_URL + SEARCH_URL);
        request.addHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        ArrayList<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("search string", "description"));
        request.setEntity(new UrlEncodedFormEntity(postParameters));

        //act
        HttpResponse response = client.execute(request);

        //assert
        String responsePayload = EntityUtils.toString(response.getEntity());
        JSONArray listOfBOs = new JSONArray(responsePayload);
        JSONObject app = listOfBOs.getJSONObject(0);

        assertEquals(BusinessObjectTest.APPLICATION_TEST_ID_1, app.getString(BusinessObject.JSON_KEY_ID));
    }
}
