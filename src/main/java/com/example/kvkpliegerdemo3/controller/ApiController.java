package com.example.kvkpliegerdemo3.controller;

import com.example.kvkpliegerdemo3.model.KvkSearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class ApiController {

    @Autowired
    private RestTemplate restTemplate;

    private static String url = "https://developers.kvk.nl/test/api/v1/zoeken?kvkNummer=69599084&pagina=1&aantal=10";

    @GetMapping("/test")
    public KvkSearchResponse getResults(){
        return restTemplate.getForObject(url, KvkSearchResponse.class);
    }
}
