package com.geoschnitzel.treasurehunt.backend;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HelloWorldController.class, HelloDatabaseController.class})
@EnableWebMvc
public class HelloWorldControllerTest {

    @MockBean
    private MessageRepository messageRepository;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void helloWorldReturnsGreeting() throws Exception {
        mvc.perform(get("/helloWorld").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World!"));
    }

    @Test
    public void restControllerCanOutputJson() throws Exception {
        mvc.perform(get("/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Ignore("Not ready yet")
    @Test
    public void databaseEndpointCanCrud() throws Exception {
        //TODO finish this test
        given(messageRepository.findAll()).willReturn(Arrays.asList(new Message[0]));

        mvc.perform(put("/helloDb").param("message", "message one").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        mvc.perform(get("/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"message one\"]"));

        mvc.perform(put("/helloDb").param("message", "message two").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        mvc.perform(get("/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"message one\",\"message two\"]"));

        mvc.perform(delete("/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        mvc.perform(get("/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}
