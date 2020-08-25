package com.vote.votemanagement.exception;


import com.vote.votemanagement.enumerator.ErrorMessages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Slf4j
@ControllerAdvice
@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "PMD.TooManyMethods"})
public class ExceptionResourceHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, CreateAssemblyException.class, CreateAssociateException.class,
            OpenAssemblyVotingSessionException.class, UserInfoIntegrationException.class, VoteProcessException.class})
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(final Exception exception, final WebRequest request) {
        log.error("Unexpected error", exception);
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ErrorMessages.UNEXPECTED_ERROR.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AssociateAlreadyExistException.class)
    public final ResponseEntity<ExceptionResponse> handleAssociateAlreadyExistException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ErrorMessages.ASSOCIATE_ALREADY_EXIST.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AssociateAlreadyVotedException.class)
    public final ResponseEntity<ExceptionResponse> handleAssociateAlreadyVotedException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ErrorMessages.ASSOCIATE_ALREADY_VOTED.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AssemblyNameAlreadyExistException.class)
    public final ResponseEntity<ExceptionResponse> handleAssemblyNameAlreadyExistException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ErrorMessages.ASSEMBLY_NAME_ALREADY_EXIST.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AssemblyNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleAssemblyNotFoundException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ErrorMessages.ASSEMBLY_NOT_FOUND.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AssemblyVotingSessionNotExistException.class)
    public final ResponseEntity<ExceptionResponse> handleAssemblyVotingSessionNoExistException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ErrorMessages.ASSEMBLY_VOTING_SESSION_NOT_EXIST.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AssemblyVotingSessionNotOpenException.class)
    public final ResponseEntity<ExceptionResponse> handleAssemblyVotingSessionNotOpenException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ErrorMessages.ASSEMBLY_VOTING_SESSION_NOT_OPEN.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AssemblyVotingSessionAlreadyOpenException.class)
    public final ResponseEntity<ExceptionResponse> handleAssemblyVotingSessionAlreadyOpenException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ErrorMessages.ASSEMBLY_VOTING_SESSION_ALREADY_OPEN.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCpfException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidCpfException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ErrorMessages.INVALID_CPF.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AssociateNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleAssociateNotFoundException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ErrorMessages.ASSOCIATE_NOT_FOUND.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidVoteException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidVoteException(final WebRequest request) {
        final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),
                ErrorMessages.INVALID_VOTE.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}