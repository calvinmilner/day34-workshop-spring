package vttp.csf.day34_workshop_spring.services;

import java.io.StringReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class ApiService {

    @Value("${api.key}")
    private String api_key;

    public static final String GIPHY_URL = "https://api.giphy.com/v1/gifs/search";

    public List<String> getGiphy(String q, int limit, String rating) {
        String url = UriComponentsBuilder.fromUriString(GIPHY_URL).queryParam("api_key", api_key)
                .queryParam("q", q).queryParam("limit", limit).queryParam("rating", rating)
                .toUriString();

        // System.out.println(url);

        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        resp = template.exchange(req, String.class);
        // System.out.println(resp.getBody());
        JsonReader reader = Json.createReader(new StringReader(resp.getBody()));
        JsonObject payload = reader.readObject();
        JsonArray data = payload.getJsonArray("data");

        return data.stream()
                .map(doc -> doc.asJsonObject())
                .map(doc -> doc.getJsonObject("images").getJsonObject("fixed_height").getString("url"))
                .toList();
    }
}
