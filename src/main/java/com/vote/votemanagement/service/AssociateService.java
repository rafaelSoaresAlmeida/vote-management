package com.vote.votemanagement.service;

import com.vote.votemanagement.dto.AssociateDto;
import com.vote.votemanagement.dto.ExternalValidateCpfResponseDto;
import com.vote.votemanagement.entity.Associate;
import com.vote.votemanagement.exception.AssociateAlreadyExistException;
import com.vote.votemanagement.exception.CreateAssociateException;
import com.vote.votemanagement.exception.InvalidCpfException;
import com.vote.votemanagement.exception.AssociateNotFoundException;
import com.vote.votemanagement.repository.AssociateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class AssociateService {

    private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";

    @Autowired
    private ExternalService externalService;

    @Autowired
    private AssociateRepository associateRepository;

    @Value("${validateCpf}")
    private boolean validateCpf;

    @Value("${timeZone}")
    private String timeZone;

    public Associate createAssociate(final AssociateDto associateDto) {
        log.info("Create a new associate registry");
        validateAssociate(associateDto.getCpf());
        final Associate associate = getAssociate(associateDto.getCreatedByAssociateCpf());

        try {
            return associateRepository.save(Associate.builder()
                    .cpf(associateDto.getCpf())
                    .name(associateDto.getName())
                    .creationDate(LocalDateTime.now(ZoneId.of(timeZone)))
                    .createdByAssociateCpf(associate.getCpf())
                    .build());
        } catch (Exception e) {
            log.error("Error in create associate registry", e);
            throw new CreateAssociateException();
        }
    }

    public Associate getAssociate(final String cpf) {
        final Associate associate = associateRepository.findByCpf(cpf);
        if (isNull(associate)) {
            log.warn("Associate not exist with this cpf: {}", cpf);
            throw new AssociateNotFoundException();
        }
        return associate;
    }


    public Iterable<Associate> getAllAssociates() {
        log.info("Get all associates");
        return associateRepository.findAll();
    }

    public void validateCpfIsAbleToVote(final String cpf) {
        if(!validateCpf){
            log.warn("CPF validation is disable in properties file");
            return;
        }

        if (!ABLE_TO_VOTE.equals(
                Optional.ofNullable(externalService.validateCpf(cpf))
                        .map(ExternalValidateCpfResponseDto::getStatus).orElse(""))) {
            log.warn("Invalid cpf: {}", cpf);
            throw new InvalidCpfException();
        }
    }

    private void validateAssociate(final String cpf) {
        log.info("Validate associate input information");
        validateCpfIsAbleToVote(cpf);
        if (!isNull(associateRepository.findByCpf(cpf))) {
            log.warn("Associate already exist with this cpf: {}", cpf);
            throw new AssociateAlreadyExistException();
        }

    }
}
