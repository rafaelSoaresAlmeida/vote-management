package com.vote.votemanagement.unit.service;

import com.vote.votemanagement.dto.AssociateDto;
import com.vote.votemanagement.dto.ExternalValidateCpfResponseDto;
import com.vote.votemanagement.entity.Associate;
import com.vote.votemanagement.exception.AssociateAlreadyExistException;
import com.vote.votemanagement.exception.CreateAssociateException;
import com.vote.votemanagement.exception.InvalidCpfException;
import com.vote.votemanagement.repository.AssociateRepository;
import com.vote.votemanagement.service.AssociateService;
import com.vote.votemanagement.service.ExternalService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    public void createNewAssociateWithSuccess() {
        AssociateDto associateDto = AssociateDto.builder()
                .cpf(CPF)
                .build();

        Associate associate = Associate.builder()
                .build();

        ExternalValidateCpfResponseDto extValidateCpfRespDto = ExternalValidateCpfResponseDto.builder()
                .status(ABLE_TO_VOTE).build();
        doReturn(extValidateCpfRespDto).when(externalService).validateCpf(Mockito.anyString());

        associateService.createAssociate(associateDto);
        Mockito.verify(associateRepository, times(1)).save(Mockito.any(Associate.class));
    }

    @Test(expected = CreateAssociateException.class)
    public void throwCreateAssociateExceptionDuringCreateNewAssociateProcess() {
        AssociateDto associateDto = AssociateDto.builder()
                .cpf(CPF)
                .build();

        ExternalValidateCpfResponseDto extValidateCpfRespDto = ExternalValidateCpfResponseDto.builder()
                .status(ABLE_TO_VOTE).build();

        doReturn(extValidateCpfRespDto).when(externalService).validateCpf(Mockito.anyString());
        doThrow(CreateAssociateException.class).when(associateRepository).save(Mockito.any(Associate.class));

        associateService.createAssociate(associateDto);
        Mockito.verify(associateRepository, times(1)).save(Mockito.any(Associate.class));
    }

    @Test
    public void validateAssociateWithoutException() {
        inputArray = new Object[]{CPF};
        ExternalValidateCpfResponseDto extValidateCpfRespDto = ExternalValidateCpfResponseDto.builder()
                .status(ABLE_TO_VOTE).build();
        doReturn(extValidateCpfRespDto).when(externalService).validateCpf(Mockito.anyString());
        ReflectionTestUtils.invokeMethod(associateService, "validateAssociate", inputArray);
    }

    @Test(expected = InvalidCpfException.class)
    public void validateAssociateWithExternalServiceInvalidCpfException() {
        inputArray = new Object[]{CPF};
        ExternalValidateCpfResponseDto extValidateCpfRespDto = ExternalValidateCpfResponseDto.builder()
                .status("UNABLE_TO_VOTE").build();
        doReturn(extValidateCpfRespDto).when(externalService).validateCpf(Mockito.anyString());
        ReflectionTestUtils.invokeMethod(associateService, "validateAssociate", inputArray);
    }

    @Test(expected = AssociateAlreadyExistException.class)
    public void validateAssociateWhenAssociateAlreadyExistException() {
        inputArray = new Object[]{CPF};
        ExternalValidateCpfResponseDto extValidateCpfRespDto = ExternalValidateCpfResponseDto.builder()
                .status(ABLE_TO_VOTE).build();
        doReturn(extValidateCpfRespDto).when(externalService).validateCpf(Mockito.anyString());
        doReturn(Associate.builder().build()).when(associateRepository).findByCpf(Mockito.anyString());
        ReflectionTestUtils.invokeMethod(associateService, "validateAssociate", inputArray);
    }
}
