package com.vote.votemanagement.unit.service;

import com.vote.votemanagement.dto.AssemblyDto;
import com.vote.votemanagement.dto.VoteDto;
import com.vote.votemanagement.entity.Assembly;
import com.vote.votemanagement.entity.Associate;
import com.vote.votemanagement.entity.Vote;
import com.vote.votemanagement.entity.VotingSession;
import com.vote.votemanagement.exception.*;
import com.vote.votemanagement.repository.AssemblyRepository;
import com.vote.votemanagement.repository.VoteRepository;
import com.vote.votemanagement.service.AssemblyService;
import com.vote.votemanagement.service.AssociateService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AssemblyServiceTest {

    private static final String CPF = "123456789";

    private static final String ASSEMBLY_NAME = "vote something";

    @InjectMocks
    private AssemblyService assemblyService;

    @Mock
    private AssemblyRepository assemblyRepository;

    @Mock
    private AssociateService associateService;

    @Mock
    private VoteRepository voteRepository;

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = CreateAssemblyException.class)
    public void throwCreateAssemblyExceptionDuringCreateAssemblyProcess() {
        final AssemblyDto assemblyDto = AssemblyDto.builder()
                .build();

        doThrow(CreateAssemblyException.class).when(assemblyRepository).save(any(Assembly.class));
        assemblyService.createAssembly(assemblyDto);
    }

    @Test(expected = AssociateAlreadyVotedException.class)
    public void voteWhenAssociatedAlreadyVoted() {

        final VoteDto voteDto = VoteDto.builder()
                .vote("não")
                .assemblyName("assemblyTest")
                .associateCpf(CPF)
                .build();

        final Associate associate = Associate.builder()
                .build();

        final VotingSession votingSession = VotingSession.builder()
                .opened(true)
                .votes(new ArrayList<>())
                .build();

        final Assembly assembly = Assembly.builder()
                .votingSession(votingSession)
                .build();

        final Vote vote = Vote.builder()
                .build();

        doReturn(associate).when(associateService).getAssociate(anyString());
        doReturn(assembly).when(assemblyRepository).findByName(anyString());
        doReturn(vote).when(voteRepository).findByAssociateCpf(anyString());

        assemblyService.vote(voteDto);
        verify(assemblyRepository, times(1)).save(any(Assembly.class));
    }

    @Test
    public void shouldNotThrowExceptionDuringValidateAssemblyOpenVotingSession() {
        final Assembly assembly = Assembly.builder()
                .build();

        doReturn(assembly).when(assemblyRepository).findByName(anyString());

        inputArray = new Object[]{ASSEMBLY_NAME};

        assertEquals(assembly, ReflectionTestUtils.invokeMethod(assemblyService, "validateAssemblyOpenVotingSession", inputArray));
    }

    @Test(expected = AssemblyVotingSessionAlreadyOpenException.class)
    public void validateAssemblyOpenVotingSessionWhenAlreadyExistAVotingSession() {

        final VotingSession votingSession = VotingSession.builder()
                .opened(true)
                .votes(new ArrayList<>())
                .build();

        final Assembly assembly = Assembly.builder()
                .votingSession(votingSession)
                .build();

        doReturn(assembly).when(assemblyRepository).findByName(anyString());

        inputArray = new Object[]{ASSEMBLY_NAME};
        ReflectionTestUtils.invokeMethod(assemblyService, "validateAssemblyOpenVotingSession", inputArray);
    }

    @Test
    public void getAssemblyWithSuccess() {
        final Assembly assembly = Assembly.builder()
                .build();

        doReturn(assembly).when(assemblyRepository).findByName(anyString());

        inputArray = new Object[]{ASSEMBLY_NAME};
        assertNotNull(ReflectionTestUtils.invokeMethod(assemblyService, "getAssembly", inputArray));
    }

    @Test(expected = AssemblyNotFoundException.class)
    public void throwAssemblyNotFoundExceptionWhenNotFoundAssemblyInGetAssembly() {
        inputArray = new Object[]{ASSEMBLY_NAME};
        ReflectionTestUtils.invokeMethod(assemblyService, "getAssembly", inputArray);
    }

    @Test
    public void shouldNotThrowExceptionDuringValidateAssemblyVotingSessionDuringVote() {
        final VotingSession votingSession = VotingSession.builder()
                .opened(true)
                .build();

        inputArray = new Object[]{votingSession};
        ReflectionTestUtils.invokeMethod(assemblyService, "validateAssemblyVotingSessionDuringVote", inputArray);
    }

    @Test(expected = AssemblyVotingSessionNotExistException.class)
    public void throwAssemblyVotingSessionNoExistExceptionWhenAssemblyVoteSessionNotExist() {
        inputArray = new Object[]{null};
        ReflectionTestUtils.invokeMethod(assemblyService, "validateAssemblyVotingSessionDuringVote", inputArray);
    }

    @Test(expected = AssemblyVotingSessionNotOpenException.class)
    public void throwAssemblyVotingSessionNotOpenExceptionWhenAssemblyVoteWasFinished() {
        final VotingSession votingSession = VotingSession.builder()
                .opened(false)
                .build();

        inputArray = new Object[]{votingSession};
        ReflectionTestUtils.invokeMethod(assemblyService, "validateAssemblyVotingSessionDuringVote", inputArray);
    }
}
