package com.vote.votemanagement.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Component
public class AssemblyVotingProducer {

    @Value("${assembly.voting.topic}")
    private String assemblyVotingTopic;

    @Autowired
    private final KafkaTemplate kafkaTemplate;

    public AssemblyVotingProducer(final KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(final @RequestBody String message) {
        kafkaTemplate.send(assemblyVotingTopic, UUID.randomUUID().toString(), message);
    }
}