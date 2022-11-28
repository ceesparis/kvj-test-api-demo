package com.example.kvkpliegerdemo3.service.impl;

import com.example.kvkpliegerdemo3.model.KvkCompany;
import com.example.kvkpliegerdemo3.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultSearchService implements SearchService {

    @Override
    public String findTypeOfSearch(String searchInput) {
        return searchInput.matches("[0-9]+") ? "kvkNummer" : "handelsnaam";
    }

    @Override
    public String createSearchRequest(String searchInput, String typeOfSearch) {
        return "https://developers.kvk.nl/test/api/v1/zoeken?" + typeOfSearch + "=" + searchInput + "&pagina=1&aantal=50";
    }

    @Override
    public String createBasicProfileRequest(String kvkNumber) {
        return "https://developers.kvk.nl/test/api/v1/basisprofielen/" + kvkNumber + "?geoData=false";
    }

    @Override
    public List<KvkCompany> convertResultsMapToCompanies(ArrayList<Map<String, String>> results) {
        return results.stream()
                .map(result -> {
                    KvkCompany company = new KvkCompany();
                    company.setKvkNumber(result.get("kvkNummer"));
                    company.setName(result.get("handelsnaam"));
                    company.setLocation(result.get("plaats"));
                    return company;
                })
                .collect(Collectors.toList());
    }

    @Override
    public String convertResponseMapToJson(Map<String, ?> response) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(response);
    }
}
