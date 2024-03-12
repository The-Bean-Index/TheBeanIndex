package com.example.beanIndex;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Services {
    private static final double COFFEE_BEAN_VALUE = 10.0;
    private static final double COCOA_BEAN_VALUE = 8.0;
   
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
    public Double getGDPForCountryAndYear(String country, int year, String beanType) {
        if (!country.equalsIgnoreCase("Country A")) {
            return null;
        }

        Map<Integer, Double> gdpData = getGDPData();

        if (!gdpData.containsKey(year)) {
            return null;
        }

        double gdp = gdpData.get(year);
        double beanValue = getBeanValue(beanType);

        return gdp / beanValue;
    }

    private double getBeanValue(String beanType) {
        if (beanType.equalsIgnoreCase("coffee")) {
            return COFFEE_BEAN_VALUE;
        } else if (beanType.equalsIgnoreCase("cocoa")) {
            return COCOA_BEAN_VALUE;
        }
        return 1.0;
    }

    private Map<Integer, Double> getGDPData() {
        Map<Integer, Double> gdpData = new HashMap<>();
        gdpData.put(2021, 100.0);
        gdpData.put(2022, 110.0);
        gdpData.put(2023, 120.0);
        gdpData.put(2024, 130.0);
        gdpData.put(2025, 140.0);

        return gdpData;
    }
    
    
    public Map<Integer, Double> getGDPForCountryAndYear(String country, int year) {
        if (!country.equalsIgnoreCase("Country A")) {
            return null;
        }

        Map<Integer, Double> gdpData = new HashMap<>();
        gdpData.put(2021, 100.0);
        gdpData.put(2022, 110.0);
        gdpData.put(2023, 120.0);
        gdpData.put(2024, 130.0);
        gdpData.put(2025, 140.0);

        return gdpData;
    }
    
    public Map<String, Double> getCurrentGDPForAllCountries() {
        Map<String, Double> gdpData = new HashMap<>();
        gdpData.put("Country A", 140.0);
        gdpData.put("Country B", 240.0);
        gdpData.put("Country C", 190.0);

        return gdpData;
    }
    public Double getGDPForCountryYear(String country, int year, String beanType) {
    // Mocking GDP data for "Country A" in terms of coffee beans
    if (!country.equalsIgnoreCase("Country A")) {
        return null;
    }

    Map<Integer, Double> gdpData = getGDPData();

    if (!gdpData.containsKey(year)) {
        return null;
    }

    double gdp = gdpData.get(year);
    double beanValue = getBeanValue(beanType);

    return gdp / beanValue;
}

}
