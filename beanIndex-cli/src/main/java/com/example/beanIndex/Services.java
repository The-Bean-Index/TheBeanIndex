package com.example.beanIndex;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Services {
   
    private final ApiClient apiClient;

    @Autowired
    public Services(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<String> getCountries() {
        return apiClient.getCountryNames();
    }

   
    public List<String> getBeans() {
        return apiClient.getBeanNames();
    }
    
    public List<Integer> getAllYears() {
            return apiClient.getAllYears();
    }

    public Integer getGDPForCountryInTermsOfBeans(String country, String beanType, int year) {
        return apiClient.getGDPForCountryInTermsOfBeans(country, beanType, year);
    }
    
    public Double getGDPRatioForCountries(String country1, String country2, String beanType, int year) {
         return apiClient.getGDPRatioForCountries(country1, country2, beanType, year);
     }

   

}
