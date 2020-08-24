package com.vote.votemanagement.service;

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

    public ExternalValidateCpfResponseDto validateCpf(final String cpf) {
        log.info("Verify cpf: {}", cpf);
        return userInfoIntegration.verifyCpf(cpf);
    }
}
