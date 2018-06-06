package com.geoschnitzel.treasurehunt.backend.test;

import com.geoschnitzel.treasurehunt.backend.DateProvider;
import com.geoschnitzel.treasurehunt.backend.model.MessageRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Message;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldApiTest {

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private DateProvider dateProvider;

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void helloWorldReturnsGreeting() throws Exception {
        given(dateProvider.currentDate()).willReturn(new Date(1524066200885L));

        mvc.perform(get("/api/helloWorld").contentType(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"message\": \"Hello World\",\n" +
                        "  \"timestamp\": \"2018-04-18T15:43:20.885+0000\"\n" +
                        "}"));
    }

    @Test
    public void restControllerCanOutputJson() throws Exception {
        mvc.perform(get("/api/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void databaseEndpointCanCrud() throws Exception {
        given(messageRepository.findAll()).willReturn(Arrays.asList(new Message[0]));
        mvc.perform(put("/api/helloDb").param("message", "message one").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(messageRepository).save(argThat(argument -> argument.getMessage().equals("message one")));

        given(messageRepository.findAll()).willReturn(Collections.singletonList(new Message(1L, "message one")));
        mvc.perform(get("/api/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"message one\"]"));

        mvc.perform(put("/api/helloDb").param("message", "message two").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(messageRepository).save(argThat(argument -> argument.getMessage().equals("message two")));

        given(messageRepository.findAll()).willReturn(Arrays.asList(new Message(1L, "message one"), new Message(2L, "message two")));
        mvc.perform(get("/api/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[\"message one\",\"message two\"]"));


        mvc.perform(delete("/api/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        verify(messageRepository).deleteAll();

        given(messageRepository.findAll()).willReturn(Collections.emptyList());
        mvc.perform(get("/api/helloDb").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

}
