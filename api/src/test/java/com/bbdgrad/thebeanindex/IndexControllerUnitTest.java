package com.bbdgrad.thebeanindex;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bbdgrad.thebeanindex.controller.IndexController;

@SpringBootTest(classes = { ThebeanindexApplication.class })
public class IndexControllerUnitTest {

    @Autowired
    private IndexController mockMvc;

    @Test
    public void testHome_ShouldReturnWelcomeMessage() throws Exception {
    
    assertEquals( mockMvc.home(), "Welcome to the Bean Index");
}}
