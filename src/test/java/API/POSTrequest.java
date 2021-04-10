package API;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class POSTrequest {
    @Test
    public void responseDeserialization() throws URISyntaxException, IOException {
        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https");
        uriBuilder.setHost("petstore.swagger.io");
        uriBuilder.setPath("v2/pet");
        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "applicaion/json");

        String jsonBody = "{\n" +
                "    \"id\": 9000,\n" +
                "    \"category\": {\n" +
                "        \"id\": 700\n" +
                "    },\n" +
                "    \"name\": \"Doggy\",\n" +
                "    \"photoUrls\": [],\n" +
                "    \"tags\": [],\n" +
                "    \"status\": \"created from java code\"\n" +
                "}";
        HttpEntity requestBody = new StringEntity(jsonBody);

        httpPost.setEntity(requestBody);

        HttpResponse response = client.execute(httpPost);
        Assert.assertEquals(HttpStatus.SC_OK,response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> parsedResponse = objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference<Map<String, Object>>() {
        });

        System.out.println(parsedResponse.get("id"));
        System.out.println(parsedResponse.get("name"));
        System.out.println(parsedResponse.get("Status"));

    }
}