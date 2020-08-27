package com.vote.votemanagement.unit.service;

import com.vote.votemanagement.dto.ExternalValidateCpfResponseDto;
import com.vote.votemanagement.entity.Associate;
import com.vote.votemanagement.exception.AssociateAlreadyExistException;
import com.vote.votemanagement.exception.AssociateNotFoundException;
import com.vote.votemanagement.repository.AssociateRepository;
import com.vote.votemanagement.service.AssociateService;
import com.vote.votemanagement.service.ExternalService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AssociateServiceTest {

    private static final String CPF = "123456789";
    private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";

    @InjectMocks
    private AssociateService associateService;

    @Mock
    private AssociateRepository associateRepository;

    @Mock
    private ExternalService externalService;

    private Object[] inputArray;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldNotThrowExceptionWhenGetAssociate() {
        inputArray = new Object[]{CPF};
        doReturn(Associate.builder().build()).when(associateRepository).findByCpf(CPF);
        associateService.getAssociate(CPF);
        verify(associateRepository, times(1)).findByCpf(CPF);
    }

    @Test(expected = AssociateNotFoundException.class)
    public void shouldThrowExceptionWhenGetAssociate() {
        inputArray = new Object[]{CPF};
        associateService.getAssociate(CPF);
        verify(associateRepository, times(1)).findByCpf(CPF);
    }

    @Test
    public void validateAssociateWithoutException() {
        inputArray = new Object[]{CPF};
        final ExternalValidateCpfResponseDto extValidateCpfRespDto = ExternalValidateCpfResponseDto.builder()
                .status(ABLE_TO_VOTE).build();
        doReturn(extValidateCpfRespDto).when(externalService).validateCpf(anyString());
        ReflectionTestUtils.invokeMethod(associateService, "validateAssociate", inputArray);
    }


    @Test(expected = AssociateAlreadyExistException.class)
    public void validateAssociateWhenAssociateAlreadyExistException() {
        inputArray = new Object[]{CPF};
        final ExternalValidateCpfResponseDto extValidateCpfRespDto = ExternalValidateCpfResponseDto.builder()
                .status(ABLE_TO_VOTE).build();
        doReturn(extValidateCpfRespDto).when(externalService).validateCpf(anyString());
        doReturn(Associate.builder().build()).when(associateRepository).findByCpf(anyString());
        ReflectionTestUtils.invokeMethod(associateService, "validateAssociate", inputArray);
    }
}
