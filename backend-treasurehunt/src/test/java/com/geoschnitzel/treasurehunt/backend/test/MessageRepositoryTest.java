package com.geoschnitzel.treasurehunt.backend.test;

import com.geoschnitzel.treasurehunt.backend.model.MessageRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Message;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.geoschnitzel.treasurehunt.util.UtilsKt.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Before
    public void clearMessages() {
        messageRepository.deleteAll();
    }

    @Test
    public void savingAndRetrievingWorks() {
        assertThat(asList(messageRepository.findAll()), hasSize(0));
        messageRepository.save(new Message("message"));
        assertThat(asList(messageRepository.findAll()), hasSize(1));
        assertThat(messageRepository.findMessageByMessage("message").getMessage(), is(equalTo("message")));
        assertThat(messageRepository.findMessageByMessage("doesntexist"), is(nullValue()));
        messageRepository.deleteAll();
        assertThat(asList(messageRepository.findAll()), hasSize(0));
    }

    @Test
    public void customRepositoryMethodWorks() {
        Message message = new Message("Hello Custom!");

        Message returnedMessage = messageRepository.storeAndReturnMessage(message);

        assertThat(returnedMessage.getId(), is(notNullValue()));
        assertThat(returnedMessage.getMessage(), is(equalTo(message.getMessage())));
    }

}
