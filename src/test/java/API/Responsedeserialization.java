package API;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class Responsedeserialization {
    @Test
    public void responseDeserialization() throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https");
        builder.setHost("petstore.swagger.io");
        builder.setPath("v2/pet/789");
        HttpGet httpGet = new HttpGet(builder.build());
        //Let server know that we expect response in JSON format
        httpGet.setHeader("Accept", "application/json");
        HttpResponse response = client.execute(httpGet);

        Assert.assertEquals(200, response.getStatusLine().getStatusCode());

        HttpEntity responseBody = response.getEntity();


        // ObjectMapper is used to map JSON to Java object
        ObjectMapper objectMapper = new ObjectMapper();
        // Deserialization: convert JSON data type to JAVA data type
        Map<String, Object> responseMap = objectMapper.readValue(responseBody.getContent(),
                new TypeReference<Map<String, Object>>() {
                });


        System.out.println(responseMap.get("name"));
        System.out.println(responseMap.get("status"));

        Assert.assertEquals(789, responseMap.get("id"));
    }


    @Test
    public void responseDeserialization2() throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder();
        builder.setScheme("https");
        builder.setHost("corona.lmao.ninja");
        builder.setPath("v2/states/Illinois");
        HttpGet httpGet = new HttpGet(builder.build());

        httpGet.setHeader("Accept","application/json");
        HttpResponse response = client.execute(httpGet);

        Assert.assertEquals(HttpStatus.SC_OK,response.getStatusLine().getStatusCode());

        HttpEntity responseBody = response.getEntity();

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> responseMap = objectMapper.readValue(responseBody.getContent(),
                new TypeReference<Map<String, Object>>() {});


        System.out.println(responseMap.get("state"));
        System.out.println(responseMap.get("population"));

        Assert.assertEquals("Illinois",responseMap.get("state"));
        Assert.assertEquals(12671821, responseMap.get("population"));
    }
    @Test
    public void responseDeserialization3() throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("http");
        uriBuilder.setHost("swapi.dev");
        uriBuilder.setPath("api/people");

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.setHeader("Accept", "application/json");

        // get executer request
        HttpResponse response = client.execute(httpGet);
        Assert.assertEquals(HttpStatus.SC_OK,response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parsedResponse = objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference<Map<String, Object>>() {});

        //Explicitly casted
        List<Map<String,Object>> resultList = (List<Map<String, Object>>) parsedResponse.get("results");

        //System.out.println(resultList);

        Map<String, Object> map0 = resultList.get(0);
        Assert.assertEquals("Luke Skywalker", map0.get("name"));

    }

}


