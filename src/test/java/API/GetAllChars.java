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

public class GetAllChars {
    @Test
    public void test1() throws URISyntaxException, IOException {

        HttpClient client = HttpClientBuilder.create().build();
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https")
                .setHost("api.got.show")
                .setPath("api/show/characters");

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        // set accept header to let server know that we expect json type in a response
        httpGet.setHeader("Accept", "application/json");

        HttpResponse response = client.execute(httpGet);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> parseResponse = objectMapper.readValue(response.getEntity().getContent(),
                new TypeReference<List<Map<String, Object>>>() {});

        System.out.println(parseResponse.size());
            }

        }

