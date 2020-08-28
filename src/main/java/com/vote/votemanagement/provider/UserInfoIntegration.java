package com.vote.votemanagement.provider;

import com.vote.votemanagement.dto.ExternalValidateCpfResponseDto;
import com.vote.votemanagement.exception.UserInfoIntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Service
public class UserInfoIntegration {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${userInfoIntegrationUrl}")
    private String userInfoIntegrationUrl;

    public ExternalValidateCpfResponseDto verifyCpf(final String cpf) {

        final HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        try {
            return restTemplate.getForEntity(userInfoIntegrationUrl + cpf, ExternalValidateCpfResponseDto.class).getBody();
        } catch (Exception exception) {
            log.error("Error to call user info integration", exception);
            throw new UserInfoIntegrationException();
        }
    }
}
