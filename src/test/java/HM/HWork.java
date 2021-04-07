package HM;

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

public class HWork {

    /*
    Convert these java objects to Json objects(create json objects):
Map<String, String>
Map<String, List<String>>
Map<String, Map<String, Object>>
Map<String, List<Integer>>
Map<String, List<Map<String, Boolean>>

{
 "count": 32,
 "previous": false,
 "results": [
        {
            "name": "Luke Skywalker",
            "height": "172",
            "mass": "77",
            "hair_color": "blond",
            "skin_color": "fair",
            "eye_color": "blue",
            "birth_year": "19BBY",
            "gender": "male",
            }
]
}
Map<String, Object> map = ....;
List<Object> resutl = map.get("results");
or
List<Map<String, String>> listOfCharacters;
->
[
        {
            "name": "Luke Skywalker",
            "height": "172",
            "mass": "77",
            "hair_color": "blond",
            "skin_color": "fair",
            "eye_color": "blue",
            "birth_year": "19BBY",
            "gender": "male",
            }
]
Map<String, String> character0 = listOfCharacters.get(0);
{
            "name": "Luke Skywalker",
            "height": "172",
            "mass": "77",
            "hair_color": "blond",
            "skin_color": "fair",
            "eye_color": "blue",
            "birth_year": "19BBY",
            "gender": "male",
            }
character0.get("name"); ->
 "Luke Skywalker"




List<Map<String, List<Map<String, List<Map<String, Map<String, List<String>>>>>
1. GET http://swapi.dev/api/people?page=1
2. Print out all character names
1. GET http://swapi.dev/api/people?page=2
2. Print out all character names
     */

    @Test
    public void task1() throws URISyntaxException, IOException {

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
