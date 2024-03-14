package com.bbdgrad.thebeanindex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bbdgrad.thebeanindex.controller.BeansController;
import com.bbdgrad.thebeanindex.controller.CountriesController;
import com.bbdgrad.thebeanindex.controller.IndexController;
import com.bbdgrad.thebeanindex.controller.YearEnumController;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = { ThebeanindexApplication.class })
public class SmokeTest {
    
    @Autowired
    private BeansController beansController;

    @Autowired
    private CountriesController countriesController;

    @Autowired
    private IndexController indexController;

    @Autowired
    private YearEnumController yearEnumController;

    @Test
	void testBeansControllerSmokeTest() throws Exception {
		assertThat(beansController).isNotNull();
	}

    @Test
	void testCountriesControllerSmokeTest() throws Exception {
		assertThat(countriesController).isNotNull();
	}

    @Test
	void testIndexControllerSmokeTest() throws Exception {
		assertThat(indexController).isNotNull();
	}

    @Test
	void testYearEnumControllerSmokeTest() throws Exception {
		assertThat(yearEnumController).isNotNull();
	}




}
