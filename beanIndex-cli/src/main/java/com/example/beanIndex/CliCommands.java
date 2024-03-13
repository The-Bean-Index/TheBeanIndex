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

    //1. List of countries allowed
    @ShellMethod(key = "list countries", value = "List all countries")
    public void listCountries() {
        List<String> countries = services.getCountries();
        for (String country : countries) {
            System.out.println(country);
        }
    }
    
    //2. List of beans allowed
   @ShellMethod(key = "list beans", value = "List all beans in the application context")
    public void listBeans() {
        List<String> beanNames = services.getBeans();
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }
    //3. List of years allowed
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

    //4. Get GDP of a country for a specific year in terms of beans
   @ShellMethod(key = "gdp country beans", value = "Get GDP of a country for a specific year in terms of beans")
    public void gdpCountryBeans(
            @ShellOption(value = {"-c", "--country"}, help = "Country name") String country,
            @ShellOption(value = {"-b", "--bean"}, help = "Bean type (e.g., Soybeans)") String beanType,
            @ShellOption(value = {"-y", "--year"}, help = "Year") int year) {

        Integer gdpAmount = services.getGDPForCountryInTermsOfBeans(country, beanType, year);

        if (gdpAmount == null) {
            System.out.println("GDP data for " + country + " and year " + year + " is not available.");
            return;
        }

        System.out.println("GDP for " + country + " in year " + year + " in terms of " + beanType + " beans:");
        System.out.println(gdpAmount);
    }

    //5. Compare GDP of countries in terms of beans
    @ShellMethod(key = "compare gdp", value = "Compare GDP of two countries in terms of beans")
    public void compareGDP(
               @ShellOption(value = {"--country1", "-c1"}, help = "First country name") String country1,
        @ShellOption(value = {"--country2", "-c2"}, help = "Second country name") String country2,
        @ShellOption(value = {"--bean", "-b"}, help = "Bean type (coffee or cocoa)") String beanType,
        @ShellOption(value = {"--year", "-y"}, help = "Year") int year) {
         Double ratio = services.getGDPRatioForCountries(country1, country2, beanType, year);

         if (ratio == null) {
             System.out.println("GDP data for one or both countries is not available.");
             return;
         }

         System.out.println("GDP ratio of " + country1 + " to " + country2 + " in terms of " + beanType + " beans:");
         System.out.println(ratio);
    }

    
}
