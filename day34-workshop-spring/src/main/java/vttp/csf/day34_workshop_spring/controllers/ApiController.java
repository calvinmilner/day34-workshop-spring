package vttp.csf.day34_workshop_spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import vttp.csf.day34_workshop_spring.services.ApiService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping(path="/api/search", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class ApiController {
    
    @Autowired
    private ApiService apiServ;

    @GetMapping
    @ResponseBody
    public ResponseEntity<String> getGiphy(@RequestParam String q, @RequestParam(defaultValue = "5") int limit, @RequestParam(defaultValue = "g") String rating) {
        
        JsonArrayBuilder builder = Json.createArrayBuilder();

        List<String> gifs = apiServ.getGiphy(q, limit, rating);
        for(String s : gifs) {
            System.out.printf("URL: %s\n", s);
        }

        gifs.stream().forEach(builder::add);

        return ResponseEntity.ok(builder.build().toString());
    }
    
}
