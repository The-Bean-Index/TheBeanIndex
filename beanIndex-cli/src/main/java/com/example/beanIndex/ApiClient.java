package com.example.beanIndex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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


    //List of countries allowed
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


    //List of beans allowed
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


    //List of years allowed
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


    //Get GDP of a country for a specific year in terms of beans
    public Integer getGDPForCountryInTermsOfBeans(String country, String beanType, int year) {
        String url = baseUrl + "/beans/gdpOfCountryInTermsOfBeans/" + country + "/" + beanType + "/" + year;

        try {
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                createHeaders(),
                new ParameterizedTypeReference<Map<String, Object>>() {
                });

            if (responseEntity.getBody() != null && responseEntity.getBody().containsKey("gdpAmount")) {
                Integer gdpAmount = (Integer) responseEntity.getBody().get("gdpAmount");
                return gdpAmount;
            } else {
                System.out.println("No response received from the server or gdpAmount not found.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public Double getGDPRatioForCountries(String country1, String country2, String beanType, int year) {
        String url = baseUrl + "/beans/gdpRatio/" + country1 + "/" + country2 + "/" + beanType + "/" + year;

        try {
            ResponseEntity<Map<String, Double>> responseEntity = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                createHeaders(),
                new ParameterizedTypeReference<Map<String, Double>>() {
                });

            if (responseEntity.getBody() != null && responseEntity.getBody().containsKey("ratio")) {
                Double ratio = responseEntity.getBody().get("ratio");
                return ratio;
            } else {
                System.out.println("No response received from the server or ratio not found.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
