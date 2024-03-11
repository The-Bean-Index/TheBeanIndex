package com.example.beanIndex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ApiClient {
    private final RestTemplate restTemplate;

    @Value("${backend.baseurl}")
    private String baseUrl;

    @Autowired
    public ApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<String> getCountryNames() {
        String countriesUrl = baseUrl + "/countries/names";

        try {
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                    countriesUrl,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {});

            if (responseEntity.getBody() != null) {
                Map<String, Object> responseBody = responseEntity.getBody();
                List<String> countryNames = (List<String>) responseBody.get("countryNames");
                return countryNames != null ? countryNames : new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
    
    public List<String> getBeanNames() {
        String beansUrl = baseUrl + "/beans/names";

        try {
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                    beansUrl,
                    org.springframework.http.HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {});

            if (responseEntity.getBody() != null) {
                Map<String, Object> responseBody = responseEntity.getBody();
                List<String> beanNames = (List<String>) responseBody.get("beanNames");
                return beanNames != null ? beanNames : new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
