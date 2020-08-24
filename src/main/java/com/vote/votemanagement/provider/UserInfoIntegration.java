package com.vote.votemanagement.provider;

import com.vote.votemanagement.dto.ExternalValidateCpfResponseDto;
import com.vote.votemanagement.exception.UserInfoIntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Service
public class UserInfoIntegration {

    @Autowired
    RestTemplate restTemplate;

    public ExternalValidateCpfResponseDto verifyCpf(final String cpf) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            //      return restTemplate.exchange("https://user-info.herokuapp.com/users/" + cpf, HttpMethod.GET, entity, String.class).getBody();
            return restTemplate.getForEntity("https://user-info.herokuapp.com/users/" + cpf, ExternalValidateCpfResponseDto.class).getBody();
        } catch (Exception exception) {
            log.error("Error to call user info integration", exception);
            throw new UserInfoIntegrationException();
        }
    }
}
