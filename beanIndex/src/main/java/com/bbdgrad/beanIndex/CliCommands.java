package com.bbdgrad.beanIndex;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import com.bbdgrad.beanIndex.models.AuthRecord;



@ShellComponent
public class CliCommands extends SecuredCommand {

    private final Services services;


    @Autowired
    public CliCommands(Services services) {
        this.services = services;
    }


    //1. List of countries allowed
    @ShellMethod(key = "list countries", value = "List all options for countries available")
    @ShellMethodAvailability("isUserSignedIn")
    public void listCountries() {
        List<String> countries = services.getCountries();
         if (countries.isEmpty()) {
            System.out.println("List of Countries is not available.");
            return;
        }
        for (String country : countries) {
            System.out.println(country);
        }
    }


    //2. List of beans allowed
    @ShellMethod(key = "list beans", value = "List all options for beans available")
    @ShellMethodAvailability("isUserSignedIn")
    public void listBeans() {
        List<String> beanNames = services.getBeans();

        if (beanNames.isEmpty()) {
            System.out.println("List of beans is not available.");
            return;
        }
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
    }


    //3. List of years allowed
    @ShellMethod(key = "list years", value = "List all options for years available")
    @ShellMethodAvailability("isUserSignedIn")
    public void getAllYears() {
        List<Integer> years = services.getAllYears();

        if (years.isEmpty()) {
            System.out.println("List of years is not available.");
            return;
        }

        System.out.println("All possible years in the database:");
        for (Integer year : years) {
            System.out.println(year);
        }
    }

    //4. Get GDP of a country for a specific year in terms of beans
    @ShellMethod(key = "gdp in beans", value = "Get GDP of a country for a specific year in terms of beans")
    @ShellMethodAvailability("isUserSignedIn")
    public void gdpCountryBeans(
        @ShellOption(value = {"-c", "--country"}, help = "Name of the country") String country,
        @ShellOption(value = {"-b", "--bean"}, help = "Type of bean") String beanType,
        @ShellOption(value = {"-y", "--year"}, help = "Year for GDP calculation") int year) {

        Double gdpAmount = services.getGDPForCountryInTermsOfBeans(country, beanType, year);

        if (gdpAmount == null) {
            System.out.println("GDP data for " + country + " and year " + year + " is not available.");
            return;
        }

        System.out.println("GDP for " + country + " in year " + year + " is equal to: ");
        System.out.println(gdpAmount + " (tons) " + "of " + beanType );
    }


    //5. Compare GDP of countries in terms of beans
    @ShellMethod(key = "gdp compare", value = "Compares GDP of two countries in terms of beans")
    @ShellMethodAvailability("isUserSignedIn")
    public void compareGDP(
        @ShellOption(value = {"-c1", "--country1"}, help = "First country name") String country1,
        @ShellOption(value = {"-c2", "--country2"}, help = "Second country name") String country2,
        @ShellOption(value = {"-b", "--bean"}, help = "Bean type") String beanType,
        @ShellOption(value = {"-y", "--year"}, help = "Year") int year) {
        Double ratio = services.getGDPRatioForCountries(country1, country2, beanType, year);

        if (ratio == null) {
            System.out.println("GDP data for one or both countries is not available.");
            return;
        }

        System.out.println("GDP ratio of " + country1 + " to " + country2 + " in terms of " + beanType + " beans:");
        System.out.println(ratio);
    }


   @ShellMethod(key = "login", value = "Login via Google")
    public void login() throws GeneralSecurityException, IOException {
        GoogleAuth auth = new GoogleAuth();
        AuthRecord authRecord = auth.signIn();
        authService.setAuth(authRecord);
        System.out.println("Logged in successfully");
    }


    @ShellMethod(key = "logout", value = "logout")
    public void logout() throws GeneralSecurityException, IOException {
        authService.logout();
        System.out.println("Logged out successfully");
    }
}