package com.example.kvkpliegerdemo3.service;

import com.example.kvkpliegerdemo3.model.KvkCompany;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface SearchService {
    String findTypeOfSearch(String searchInput);

    String createRequest(String searchInput, String typeOfSearch);

    List<KvkCompany> convertResultsMapToCompanies(ArrayList<Map<String, String>> results);

    String convertResponseMapToJson(Map<String, ?> response) throws JsonProcessingException;
}
