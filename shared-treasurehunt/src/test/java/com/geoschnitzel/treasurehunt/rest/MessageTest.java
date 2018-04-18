package com.geoschnitzel.treasurehunt.rest;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class MessageTest {

    @Test
    public void messageHasGettersAndConstructor() {
        Message message = new Message("Hello World", new Date());
        assertThat(message.getMessage(), is(equalTo("Hello World")));
        assertThat(message.getMessageUpperCase(), is(equalTo("HELLO WORLD")));
    }
}
