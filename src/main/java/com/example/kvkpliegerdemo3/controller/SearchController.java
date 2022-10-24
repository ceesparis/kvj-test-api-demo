package com.example.kvkpliegerdemo3.controller;

import com.example.kvkpliegerdemo3.form.RegistrationForm;
import com.example.kvkpliegerdemo3.form.SearchInputForm;
import com.example.kvkpliegerdemo3.model.KvkCompany;
import com.example.kvkpliegerdemo3.service.SearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller("SearchController")
public class SearchController {

    private static final Logger LOG = LoggerFactory.getLogger(SearchController.class);
    @Resource
    private RestTemplate restTemplate;

    @Resource
    private SearchService searchService;

    @PostMapping(value="/search")
    protected RedirectView getSearchInput(@ModelAttribute("SearchInputForm") SearchInputForm form,
              RedirectAttributes redirectAttributes) {
        String searchInput = form.getSearchInput();
        redirectAttributes.addFlashAttribute("searchInput", searchInput);
        redirectAttributes.addFlashAttribute("typeOfSearch", searchService.findTypeOfSearch(searchInput));

        return new RedirectView("/search/input", true);
    }

    @GetMapping(value="/search/input")
    @SuppressWarnings("unchecked")
    protected String search(@ModelAttribute("SearchInputForm") SearchInputForm form, HttpServletRequest request,
                                    RedirectAttributes redirectAttributes){
        Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);

        if (inputFlashMap ==  null) {
            String errorMessage = "Something went wrong on our end, and we lost your search query. Please try again!";
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);

            return "redirect:" + request.getHeader("referer");
        }

        String searchInput = (String) inputFlashMap.get("searchInput");
        String typeOfSearch = (String) inputFlashMap.get("typeOfSearch");
        String url = searchService.createRequest(searchInput, typeOfSearch);

        try {
            Map<String, ?> response = restTemplate.getForObject(url, HashMap.class);

            try {
                String json = searchService.convertResponseMapToJson(response);
                redirectAttributes.addFlashAttribute("json", json);
            } catch (JsonProcessingException jsonProcessingException) {
                LOG.error("Response could not be converted back to JSON");
            }

            List<KvkCompany>  companies = searchService.convertResultsMapToCompanies((ArrayList<Map<String, String>>) response.get("resultaten"));
            redirectAttributes.addFlashAttribute("request", url);
            redirectAttributes.addFlashAttribute("results", companies);
        } catch (HttpClientErrorException httpClientErrorException) {
            String errorMessage = "No companies were found! Your search term was \'" + searchInput + "\'.";
            request.getSession().invalidate();
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            LOG.error(errorMessage, httpClientErrorException);
        } catch (HttpServerErrorException httpServerErrorException) {
            String errorMessage = "The search failed. An error occurred on the server side. Please try again later.";
            request.getSession().invalidate();
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            LOG.error(errorMessage, httpServerErrorException);
        }
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping(value="/select-company")
    protected RedirectView selectCompany(HttpServletRequest request, RedirectAttributes redirectAttributes,
                                   @ModelAttribute("RegistrationForm") RegistrationForm form) {
        redirectAttributes.addFlashAttribute("form", form);
        return new RedirectView("register", true);
    }


    @GetMapping("/")
    public String viewIndex(HttpServletRequest request){
        request.setAttribute("enabled", false);
        return "searchpage";
    }



    @GetMapping("/select-company")
    public String viewRegistry(HttpServletRequest request){
        return "registrypage";
    }


}
