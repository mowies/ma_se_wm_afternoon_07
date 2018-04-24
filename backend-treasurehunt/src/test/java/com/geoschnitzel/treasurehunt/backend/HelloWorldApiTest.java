package com.geoschnitzel.treasurehunt.backend;

import com.geoschnitzel.treasurehunt.backend.api.HelloDatabaseApi;
import com.geoschnitzel.treasurehunt.backend.api.HelloWorldApi;
import com.geoschnitzel.treasurehunt.backend.model.Message;
import com.geoschnitzel.treasurehunt.backend.repository.MessageRepository;

import org.junit.Before;
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
import java.util.Collections;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HelloWorldApi.class, HelloDatabaseApi.class})
@EnableWebMvc
public class HelloWorldApiTest {

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

    @Test
    public void databaseEndpointCanCrud() throws Exception {
        given(messageRepository.findAll()).willReturn(Arrays.asList(new Message[0]));
        mvc.perform(put("/helloDb").param("message", "message one").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(messageRepository).save(argThat(argument -> argument.getMessage().equals("message one")));

        given(messageRepository.findAll()).willReturn(Collections.singletonList(new Message(1L, "message one")));
        mvc.perform(get("/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"message one\"]"));

        mvc.perform(put("/helloDb").param("message", "message two").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(messageRepository).save(argThat(argument -> argument.getMessage().equals("message two")));

        given(messageRepository.findAll()).willReturn(Arrays.asList(new Message(1L, "message one"), new Message(2L, "message two")));
        mvc.perform(get("/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"message one\",\"message two\"]"));


        mvc.perform(delete("/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(messageRepository).deleteAll();

        given(messageRepository.findAll()).willReturn(Collections.emptyList());
        mvc.perform(get("/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}
