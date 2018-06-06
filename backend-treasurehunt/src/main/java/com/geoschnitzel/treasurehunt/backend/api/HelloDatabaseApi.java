package com.geoschnitzel.treasurehunt.backend.api;

import com.geoschnitzel.treasurehunt.backend.model.MessageRepository;
import com.geoschnitzel.treasurehunt.backend.schema.Message;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/helloDb")
public class HelloDatabaseApi {

    private final MessageRepository messageRepository;

    public HelloDatabaseApi(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @DeleteMapping
    public void clearMessages() {
        messageRepository.deleteAll();
    }

    @PutMapping
    public void addMessage(@RequestParam("message") String message) {
        messageRepository.save(new Message(message));
    }

    @GetMapping
    public List<String> getMessages() {
        return StreamSupport.stream(messageRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparingLong(Message::getId))
                .map(Message::getMessage)
                .collect(Collectors.toList());
    }
}
