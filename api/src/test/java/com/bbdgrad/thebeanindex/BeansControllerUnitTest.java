package com.bbdgrad.thebeanindex;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bbdgrad.thebeanindex.controller.BeansController;
import com.bbdgrad.thebeanindex.dto.BeanGDPDto;
import com.bbdgrad.thebeanindex.dto.GDPDto;
import com.bbdgrad.thebeanindex.model.Beans;
import com.bbdgrad.thebeanindex.model.Countries;
import com.bbdgrad.thebeanindex.model.GDP;
import com.bbdgrad.thebeanindex.model.YearEnum;
import com.bbdgrad.thebeanindex.repository.BeansRepository;
import com.bbdgrad.thebeanindex.repository.CountriesRepository;
import com.bbdgrad.thebeanindex.repository.GdpRepository;
import com.bbdgrad.thebeanindex.repository.YearEnumRepository;

public class BeansControllerUnitTest {
	
    @Autowired
    private BeansController controller;

    @MockBean
    private CountriesRepository countriesRepository;

    @MockBean
    private BeansRepository beansRepository;

    @MockBean
    private YearEnumRepository yearEnumRepository;

    @MockBean
    private GdpRepository gdpRepository;

    @Test
    public void testGetGDPofCountryInBeans_Success() throws Exception {
        String countryName = "China";
        String beanName = "Soybeans";
        Integer year = 2023;
        int years = 2023;

        Countries mockCountry = new Countries(2, countryName);
        Beans mockBean = new Beans(9, beanName, new BigDecimal("959"));
        YearEnum mockYear = new YearEnum(2, 2023, new ArrayList<>());
        GDP mockGDP = new GDP(2, mockCountry, mockYear, new BigDecimal("14433.59"));

        // Mockito.when(countriesRepository.findReferenceByName(countryName)).thenReturn(Optional.of(mockCountry));
        // Mockito.when(beansRepository.findReferenceByName(beanName)).thenReturn(Optional.of(mockBean));
        // Mockito.when(yearEnumRepository.findReferenceByYear(year)).thenReturn(Optional.of(mockYear));
        // Mockito.when(gdpRepository.findAllReferencesByYearYearAndCountryName(year, countryName))
        //         .thenReturn(Optional.of(mockGDP));

        // ResponseEntity<BeanGDPDto> response = (ResponseEntity<BeanGDPDto>) controller
        //         .getGDPOfCountryInBeans(countryName, beanName, years);

        // assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        // GDPDto expectedDto = GDPDto.toDto(mockGDP);
        // assertThat(response.getBody().getGdpAmount())
        //         .isEqualTo(expectedDto.getGdpAmount().divide((new BigDecimal(959)), 2, RoundingMode.HALF_UP));
        // assertThat(response.getBody().getYear()).isEqualTo(expectedDto.getYear());
    }
}
