package API;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class CountLenght {
    

    @Test
    public void test() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https").setHost("catfact.ninja").setPath("fact");

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        // set accept header to let server know that we expect json type in a response
        httpGet.setHeader("Accept", "application/json");

        HttpResponse response = client.execute(httpGet);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parsedResponse = objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference<Map<String, Object>>() {
                });
        String fact = String.valueOf(parsedResponse.get("fact"));
        int length = (int) parsedResponse.get("length");

        Assert.assertEquals(fact.length(), length);

    }

    /*
    validate that response has only 1 fact cats
     */
    @Test
    public void test2() throws IOException, URISyntaxException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder builder = new URIBuilder();
// catfact.ninja/fact
        builder.setScheme("https");
        builder.setHost("catfact.ninja");
        builder.setPath("fact");
        HttpGet httpGet = new HttpGet(builder.build());
        httpGet.setHeader("Accept", "application/json");
        HttpResponse response = client.execute(httpGet);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseMap = objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference<Map<String, Object>>() {
                });
        String fact = (String) responseMap.get("fact");
        System.out.println(fact);
        int length = (int) responseMap.get("length");
        System.out.println(length);
        Assert.assertEquals("They are not equal", fact.length(), length);
    }


    @Test
    public void test3() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https").setHost("catfact.ninja").setPath("facts");

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        // set accept header to let server know that we expect json type in a response
        httpGet.setHeader("Accept", "application/json");

        HttpResponse response = client.execute(httpGet);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> Response = objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference<Map<String, Object>>() {
                });

        int factPerPage = Integer.parseInt(String.valueOf(Response.get("per_page")));

        List<Map<String, Object>> listOfFact = (List<Map<String, Object>>) Response.get("data");

        System.out.println(listOfFact.size());
        Assert.assertEquals(1, listOfFact.size());

    }


    /*
    Print links and ...
     */
    @Test
    public void test4() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https")
                .setHost("catfact.ninja")
                .setPath("facts")
                .setParameter("limit", "2");

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        // set accept header to let server know that we expect json type in a response
        httpGet.setHeader("Accept", "application/json");

        HttpResponse response = client.execute(httpGet);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parseResponse = objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference<Map<String, Object>>() {
                });

        List<Map<String, Object>> linksList = (List<Map<String, Object>>) parseResponse.get("links");


        for (Map<String, Object> map : linksList) {
            // Getting urls one by one
            String tempUrl = String.valueOf(map.get("url"));
            if (tempUrl != "null") {
                HttpClient tempClient = HttpClientBuilder.create().build();
                System.out.println(tempUrl);


                URIBuilder uriBuilder1 = new URIBuilder(tempUrl);
                // Define new get method for every temp URL
                HttpGet get = new HttpGet(uriBuilder1.build());
                get.setHeader("Accept", "application/json");
                // Execute a get tempURL
                HttpResponse tempResponse = tempClient.execute(get);
                Assert.assertEquals(HttpStatus.SC_OK, tempResponse.getStatusLine().getStatusCode());


            }
        }
    }

}
