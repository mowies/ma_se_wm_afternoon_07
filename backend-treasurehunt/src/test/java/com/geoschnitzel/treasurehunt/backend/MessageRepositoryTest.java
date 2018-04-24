package com.geoschnitzel.treasurehunt.backend;

import com.geoschnitzel.treasurehunt.backend.model.Message;
import com.geoschnitzel.treasurehunt.backend.repository.MessageRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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

    private <T> List<T> asList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
    }
}
