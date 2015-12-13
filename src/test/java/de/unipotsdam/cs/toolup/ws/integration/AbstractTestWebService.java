package de.unipotsdam.cs.toolup.ws.integration;


import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * In contrast to the UnitTests, this integration tests require a running web server to succeed.
 */
public abstract class AbstractTestWebservice {

    protected final static String TOOLUP_URL = "http://localhost:8080/toolup";

    protected HttpClient client = HttpClientBuilder.create().build();
}
