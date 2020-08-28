package com.vote.votemanagement.service;

import com.vote.votemanagement.component.AssemblyVotingProducer;
import com.vote.votemanagement.dto.ExternalValidateCpfResponseDto;
import com.vote.votemanagement.provider.UserInfoIntegration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExternalService {

    @Autowired
    private UserInfoIntegration userInfoIntegration;

    @Autowired
    private AssemblyVotingProducer assemblyVotingProducer;

    public ExternalValidateCpfResponseDto validateCpf(final String cpf) {
        log.info("Verify cpf: {}", cpf);
        return userInfoIntegration.verifyCpf(cpf);
    }

    public void publishAssemblyMessageOnKafka(final String message) {
        log.info("Publish Assembly message: [{}] on Kafka", message);
        try {
            assemblyVotingProducer.send(message);
        } catch (Exception e) {
            log.error("Error to publish message on Kafka", e);
        }
    }
}
