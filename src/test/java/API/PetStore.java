package API;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class PetStore {

    /*
    1. Launch/instantiate POSTMAN/client
    2. Find/ provide an endpoint/URL/URI
    3. Define a http request (action)
    4. Send/execute a request
    5. Check the status code

     */

        @Test
        public void getTest3() throws URISyntaxException, IOException {
            HttpClient httpClient= HttpClientBuilder.create().build();
            URIBuilder uriBuilder=new URIBuilder();
            uriBuilder.setScheme("https");
            uriBuilder.setHost("petstore.swagger.io");
            uriBuilder.setPath("v2/pet/789");
            HttpGet get=new HttpGet(uriBuilder.build());
            HttpResponse response=httpClient.execute(get);
            int statusCode=response.getStatusLine().getStatusCode();
            Assert.assertEquals(200,statusCode);
            Assert.assertTrue(statusCode!=404);

        }


    }


