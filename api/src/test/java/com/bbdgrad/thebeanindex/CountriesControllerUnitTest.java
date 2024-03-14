package com.bbdgrad.thebeanindex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.bbdgrad.thebeanindex.controller.CountriesController;
import com.bbdgrad.thebeanindex.dto.CountriesDto;
import com.bbdgrad.thebeanindex.dto.CountriesResponseDto;
import com.bbdgrad.thebeanindex.model.Countries;
import com.bbdgrad.thebeanindex.repository.CountriesRepository;

@SpringBootTest(classes = { ThebeanindexApplication.class })
public class CountriesControllerUnitTest {

    @Mock
    private CountriesRepository countriesRepositoryMock;

    private CountriesController countriesController;

    @BeforeEach
    public void setUp() {
        countriesController = new CountriesController(countriesRepositoryMock);
    }

    @Test
    public void testGetAllCountries_ShouldReturnAllCountriesDto() throws Exception {

        List<Countries> countriesList = new ArrayList<>();
        countriesList.add(new Countries(1, "United States"));
        countriesList.add(new Countries(2, "China"));
        countriesList.add(new Countries(3, "Japan"));
        countriesList.add(new Countries(4, "Germany"));
        countriesList.add(new Countries(5, "France"));
        countriesList.add(new Countries(6, "United Kingdom"));
        countriesList.add(new Countries(7, "Brazil"));
        countriesList.add(new Countries(8, "Italy"));
        countriesList.add(new Countries(9, "India"));
        countriesList.add(new Countries(10, "Russia"));
        Mockito.when(countriesRepositoryMock.findAll()).thenReturn(countriesList);

        List<CountriesDto> expectedCountriesDtoList = countriesList.stream()
                .map(CountriesDto::toDto)
                .collect(Collectors.toList());

        CountriesResponseDto actualResponse = countriesController.getAllCountries();

        // Assertions
        assertNotNull(actualResponse);
        List<CountriesDto> actualCountries = actualResponse.getCountries();

        assertEquals(expectedCountriesDtoList.size(), actualCountries.size());

        for (int i = 0; i < actualCountries.size(); i++) {
            CountriesDto expectedCountryDto = expectedCountriesDtoList.get(i);
            CountriesDto actualCountry = actualCountries.get(i);

            assertEquals(expectedCountryDto.getId(), actualCountry.getId());
            assertEquals(expectedCountryDto.getName(), actualCountry.getName());
        }
    }
}
