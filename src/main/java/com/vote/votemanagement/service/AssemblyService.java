package com.vote.votemanagement.service;

import com.google.gson.Gson;
import com.vote.votemanagement.dto.AssemblyDto;
import com.vote.votemanagement.dto.VoteDto;
import com.vote.votemanagement.dto.VotingSessionDto;
import com.vote.votemanagement.entity.Assembly;
import com.vote.votemanagement.entity.Associate;
import com.vote.votemanagement.entity.Vote;
import com.vote.votemanagement.entity.VotingSession;
import com.vote.votemanagement.exception.*;
import com.vote.votemanagement.repository.AssemblyRepository;
import com.vote.votemanagement.repository.VoteRepository;
import com.vote.votemanagement.repository.VotingSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class AssemblyService {

    @Autowired
    private AssemblyRepository assemblyRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AssociateService associateService;

    @Autowired
    private VotingSessionRepository votingSessionRepository;

    @Autowired
    ExternalService externalService;

    @Value("${timeZone}")
    private String timeZone;

    @Value("${publishMessageOnKafka}")
    private boolean publishMessageOnKafka;

    public Assembly createAssembly(final AssemblyDto assemblyDto) {
        log.info("Create a new assembly registry");

        if (nonNull(assemblyRepository.findByName(assemblyDto.getName()))) {
            log.warn("An assembly with this name: {} already exist", assemblyDto.getName());
            throw new AssemblyNameAlreadyExistException();
        }

        final Associate associate = associateService.getAssociate(assemblyDto.getAssociateCpf());

        try {
            return assemblyRepository.save(Assembly.builder()
                    .name(assemblyDto.getName())
                    .createdBy(associate)
                    .creationDate(LocalDateTime.now(ZoneId.of(timeZone)))
                    .build());
        } catch (Exception e) {
            log.error("Error in create associate registry", e);
            throw new CreateAssemblyException();
        }
    }

    public Assembly findAssemblyByName(final String assemblyName, final String associateCpf) {
        log.info("Get an assembly by name: {}", assemblyName);
        associateService.getAssociate(associateCpf);
        return assemblyRepository.findByName(assemblyName);
    }

    public Iterable<Assembly> getAllAssemblies() {
        log.info("Get all assemblies");
        return assemblyRepository.findAll();
    }

    public Assembly openAssemblyVotingSession(final VotingSessionDto votingSessionDto) {
        log.info("Open voting session operation for assembly: {}", votingSessionDto.getAssemblyName());

        Assembly assembly = validateAssemblyOpenVotingSession(votingSessionDto.getAssemblyName());

        final Associate associate = associateService.getAssociate(votingSessionDto.getAssociateCpf());

        final int votingSessionDuration = votingSessionDto.getVotingSessionDuration();
        final VotingSession votingSession = VotingSession.builder()
                .startDate(LocalDateTime.now(ZoneId.of(timeZone)))
                .finishDate(LocalDateTime.now(ZoneId.of(timeZone)).plusMinutes(isNull(votingSessionDuration) || votingSessionDuration == 0 ? 1 : votingSessionDuration))
                .votes(new ArrayList<>())
                .opened(true)
                .openedBy(associate)
                .yes(Long.parseLong("0"))
                .no(Long.parseLong("0"))
                .build();

        assembly.setVotingSession(votingSession);

        try {
            assembly = assemblyRepository.save(assembly);
        } catch (Exception exception) {
            log.error("Error to open assembly voting session with name: {}", votingSessionDto.getAssemblyName());
            throw new OpenAssemblyVotingSessionException();
        }

        if (publishMessageOnKafka) {
            log.warn("Publish message on Kafka property is enable");
            externalService.publishAssemblyMessageOnKafka(new Gson().toJson(assembly));
        }

        return assembly;
    }

    public void vote(final VoteDto voteDto) {
        log.info("Voting for associate with cpf: {}", voteDto.getAssociateCpf());

        associateService.validateCpfIsAbleToVote(voteDto.getAssociateCpf());

        final Assembly assembly = getAssembly(voteDto.getAssemblyName());

        validateAssemblyVotingSessionDuringVote(assembly.getVotingSession());


        if (nonNull(voteRepository.findByAssociateCpf(voteDto.getAssociateCpf()))) {
            log.warn("Associate with cpf {} already voted", voteDto.getAssociateCpf());
            throw new AssociateAlreadyVotedException();
        }

        final boolean voteValue = parseVote(voteDto.getVote());

        final Associate associate = associateService.getAssociate(voteDto.getAssociateCpf());

        final Vote vote = Vote.builder()
                .value(voteValue)
                .associate(associate)
                .createdDate(LocalDateTime.now(ZoneId.of(timeZone)))
                .votingSession(assembly.getVotingSession())
                .build();

        Long temp;
        if (voteValue) {
            temp = assembly.getVotingSession().getYes();
            assembly.getVotingSession().setYes(++temp);
        } else {
            temp = assembly.getVotingSession().getNo();
            assembly.getVotingSession().setNo(++temp);
        }

        assembly.getVotingSession().getVotes().add(vote);

        try {
            assemblyRepository.save(assembly);
        } catch (Exception exception) {
            log.error("Error in vote process to associate with cpf: {}", voteDto.getAssociateCpf(), exception);
            throw new VoteProcessException();
        }
    }

    private boolean parseVote(final String vote) {
        if ("SIM".equalsIgnoreCase(vote)) {
            return true;
        } else if ("NÃO".equalsIgnoreCase(vote)) {
            return false;
        }

        log.warn("Invalid vote: {}", vote);
        throw new InvalidVoteException();
    }

    private Assembly validateAssemblyOpenVotingSession(final String assemblyName) {
        log.info("Validate assembly open voting session with name: {}", assemblyName);
        final Assembly assembly = getAssembly(assemblyName);

        if (nonNull(assembly.getVotingSession())) {
            log.warn("Already exist a voting session for assembly with name: {}", assemblyName);
            throw new AssemblyVotingSessionAlreadyOpenException();
        }

        return assembly;
    }

    private Assembly getAssembly(final String assemblyName) {
        log.info("get assembly with name: {} in database", assemblyName);
        final Assembly assembly = assemblyRepository.findByName(assemblyName);
        if (isNull(assembly)) {
            log.warn("Any assembly with this name: {}  was not found", assemblyName);
            throw new AssemblyNotFoundException();
        }
        return assembly;
    }

    private void validateAssemblyVotingSessionDuringVote(final VotingSession votingSession) {
        log.info("Validate voting session");
        if (isNull(votingSession)) {
            log.warn("Assembly voting session  was not found");
            throw new AssemblyVotingSessionNotExistException();
        }

        if (!votingSession.isOpened()) {
            log.warn("Assembly voting session is not open");
            throw new AssemblyVotingSessionNotOpenException();
        }
    }

    public void verifyOpenVotingSessions() {
        final List<VotingSession> votingSessions = votingSessionRepository.findByOpened(true);
        for (final VotingSession votingSession : votingSessions) {
            closeVotingSession(votingSession);
        }
    }

    private void closeVotingSession(final VotingSession votingSession) {
        if (LocalDateTime.now(ZoneId.of(timeZone)).isAfter(votingSession.getFinishDate())) {
            try {
                votingSession.setOpened(false);
                votingSessionRepository.save(votingSession);
            } catch (Exception e) {
                log.error("Error to close voting session with id: {}", votingSession.getId(), e);
            }
        }
    }
}
