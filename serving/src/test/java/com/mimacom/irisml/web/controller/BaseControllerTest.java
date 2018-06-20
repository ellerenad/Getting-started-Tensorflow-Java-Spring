package com.mimacom.irisml.web.controller;

import com.mimacom.tensorflowdemo.WebModuleTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

/**
 * Base class for the test of the controllers
 */
@SpringBootTest(classes = {WebModuleTestConfig.class})
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseControllerTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected HttpMessageConverter mappingJackson2HttpMessageConverter;


    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    @Before
    public void setUp() throws Exception {

        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .build();
    }

    @Test
    public void testInit() throws Exception {
        assertTrue(true);
    }
}
