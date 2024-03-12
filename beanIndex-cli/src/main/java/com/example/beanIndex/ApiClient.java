package com.example.beanIndex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ApiClient {
    private final RestTemplate restTemplate;
    private final AuthService authService;

    @Value("${backend.baseurl}")
    private String baseUrl;


    @Autowired
    public ApiClient(RestTemplate restTemplate, AuthService authService) {
        this.restTemplate = restTemplate;
        this.authService = authService;
    }


    public HttpEntity<Void> createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authService.getIdToken());
        return new HttpEntity<>(headers);
    }


    public List<String> getCountryNames() {
        String countriesUrl = baseUrl + "/countries/names";

        try {
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                countriesUrl,
                org.springframework.http.HttpMethod.GET,
                createHeaders(),
                new ParameterizedTypeReference<Map<String, Object>>() {
                });

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
                createHeaders(),
                new ParameterizedTypeReference<Map<String, Object>>() {
                });

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


    public List<Integer> getAllYears() {
        String allYearsUrl = baseUrl + "/year/all";

        try {
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                allYearsUrl,
                org.springframework.http.HttpMethod.GET,
                createHeaders(),
                new ParameterizedTypeReference<Map<String, Object>>() {
                });

            if (responseEntity.getBody() != null) {
                Map<String, Object> responseBody = responseEntity.getBody();
                List<Integer> years = (List<Integer>) responseBody.get("years");
                return years != null ? years : new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    public Integer getGDPForCountryInTermsOfBeans(String country, String beanType, int year) {
        String url = baseUrl + "/beans/gdpOfCountryInTermsOfBeans/" + country + "/" + beanType + "/" + year;

        try {
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                createHeaders(),
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
            );

            if (responseEntity.getBody() != null) {
                Map<String, Object> responseBody = responseEntity.getBody();
                Integer gdpAmount = (Integer) responseBody.get("gdpAmount");
                return gdpAmount;
            } else {
                System.out.println("No response received from the server.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
