package com.bbdgrad.thebeanindex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bbdgrad.thebeanindex.controller.YearEnumController;
import com.bbdgrad.thebeanindex.Dtos.YearEmunDto;
import com.bbdgrad.thebeanindex.Dtos.YearEnumAllowedYearsDto;
import com.bbdgrad.thebeanindex.Dtos.YearsEnumDto;
import com.bbdgrad.thebeanindex.model.YearEnum;
import com.bbdgrad.thebeanindex.repository.YearEnumRepository;


@SpringBootTest(classes = { ThebeanindexApplication.class })
public class YearEnumControllerUnitTest {

    @Autowired
    private YearEnumController yearEnumController;

    @Mock
    private YearEnumRepository yearEnumRepositoryMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCountriesByYear_ShouldReturnNotFoundForMissingYear() throws Exception {
        int year = 2029;
        String beanName = "SoyBeans";
        when(yearEnumRepositoryMock.findAllReferencesByYear(year)).thenReturn(Optional.empty());

        ResponseEntity<YearEmunDto> response = yearEnumController.getCountriesByYear(year,beanName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    

	@Test
    public void testGetCountriesByYear_ShouldReturnYearEmunDtoForExistingYear() throws Exception {
        int year = 2023;
        String beanName = "SoyBeans";
        YearEnum yearEnum = new YearEnum(9,2023, new ArrayList<>());
        when(yearEnumRepositoryMock.findAllReferencesByYear(year)).thenReturn(Optional.of(yearEnum));

        ResponseEntity<YearEmunDto> response = yearEnumController.getCountriesByYear(year,beanName);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        YearEmunDto dto = response.getBody();
        assertEquals(year, dto.getYear()); 
    }

	@Test
    public void testGetCountryGdpForLast5Years_ShouldReturnYearsEnumDto() throws Exception {
        String country = "Brazil";
        int currentYear = 2024;
        String beanName = "SoyBeans";
        

        List<YearEnum> yearEnums = new ArrayList<>();
        for (int year = currentYear - 5; year < currentYear; year++) {
            yearEnums.add(new YearEnum(9,year, new ArrayList<>())); 
        }

        when(yearEnumRepositoryMock.findAllReferencesByYear(anyInt())).thenReturn(
                Optional.of(new YearEnum(9, currentYear, null))); 

        ResponseEntity<YearsEnumDto> response = yearEnumController.getCountryGdpForLast5Years(country,beanName);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        YearsEnumDto yearsDto = response.getBody();
        assertEquals(5, yearsDto.getYears().size()); 
    }

    @Test
    public void testGetAllYears_ShouldReturnAllYears() throws Exception {
        YearEnumAllowedYearsDto responseDto = yearEnumController.getAllYears();

        // Assertions
        assertNotNull(responseDto); 
        List<Integer> actualYears = responseDto.getYears();
        assertEquals(10, actualYears.size()); 

        
        List<Integer> expectedYears = Arrays.asList(2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024);
        assertEquals(expectedYears, actualYears);
    }

}
