package com.example.beanIndex;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class CliCommands {

    private final Services services;

    @Autowired
    public CliCommands(Services services) {
        this.services = services;
    }

    //1. list of countries
    @ShellMethod(key = "list countries", value = "List all countries")
    public void listCountries() {
        List<String> countries = services.getCountries();
        for (String country : countries) {
            System.out.println(country);
        }
    }
    
    //2. list of beans
   @ShellMethod(key = "list beans", value = "List all beans in the application context")
    public void listBeans() {
        List<String> beanNames = services.getBeans();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
    //3. Get all possible years from the database
    @ShellMethod(key = "all years", value = "Get all possible years")
    public void getAllYears() {
        List<Integer> years = services.getAllYears();

        if (years.isEmpty()) {
            System.out.println("No years found in the database.");
            return;
        }

        System.out.println("All possible years in the database:");
        for (Integer year : years) {
            System.out.println(year);
        }
    }
    //3. Get GDP of a country for a specific year in terms of beans
    @ShellMethod(key = "gdp for year", value = "Get GDP for a specific country and year in terms of beans")
    public void gdpForYear(
            @ShellOption(value = {"-c", "--country"}, help = "Country name") String country,
            @ShellOption(value = {"-y", "--year"}, help = "Year") int year,
            @ShellOption(value = {"-b", "--bean"}, help = "Bean type (coffee or cocoa)") String beanType) {

        Double gdpInBeans = services.getGDPForCountryAndYear(country, year, beanType);

        if (gdpInBeans == null) {
            System.out.println("GDP data for " + country + " and year " + year + " is not available.");
            return;
        }

        System.out.println("GDP for " + country + " in year " + year + " in terms of " + beanType + " beans:");
        System.out.println(gdpInBeans + " " + beanType + " beans");
    }
   

    //5. Report of all countries GDP (ranked)
    @ShellMethod(key = "ranked gdp", value = "Rank countries by GDP for the current year")
    public void rankedCurrentGDP() {
        Map<String, Double> gdpData = services.getCurrentGDPForAllCountries();

        List<Map.Entry<String, Double>> sortedGDP = gdpData.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        System.out.println("Ranked GDP for current year:");
        int rank = 1;
        for (Map.Entry<String, Double> entry : sortedGDP) {
            System.out.println(rank + ". " + entry.getKey() + ": " + entry.getValue());
            rank++;
        }
    }
   @ShellMethod(key = "compare gdp", value = "Compare GDP of two countries in terms of beans")
public void compareGDP(
        @ShellOption(value = {"-c1", "--country1"}, help = "First country name") String country1,
        @ShellOption(value = {"-c2", "--country2"}, help = "Second country name") String country2,
        @ShellOption(value = {"-b", "--bean"}, help = "Bean type (coffee or cocoa)") String beanType) {

    Double gdpCountry1 = services.getGDPForCountryYear(country1, 2021, beanType);
    Double gdpCountry2 = services.getGDPForCountryYear(country2, 2022, beanType);

    if (gdpCountry1 == null || gdpCountry2 == null) {
        System.out.println("GDP data for one or both countries is not available.");
        return;
    }

    if (gdpCountry2 == 0) {
        System.out.println("Cannot divide by zero.");
        return;
    }

    double ratio = gdpCountry1 / gdpCountry2;

    System.out.println("GDP ratio of " + country1 + " to " + country2 + " in terms of " + beanType + " beans:");
    System.out.println(ratio);
}
}
