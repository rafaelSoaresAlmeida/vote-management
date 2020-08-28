package com.vote.votemanagement.controller;

import com.vote.votemanagement.dto.AssemblyDto;
import com.vote.votemanagement.dto.VoteDto;
import com.vote.votemanagement.dto.VotingSessionDto;
import com.vote.votemanagement.entity.Assembly;
import com.vote.votemanagement.entity.Associate;
import com.vote.votemanagement.service.AssemblyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/assembly", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Assembly service controller")
@Slf4j
@Controller
public class AssemblyController {

    private static final String BAD_REQUEST = "Bad Request";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server ErrorMessage";

    @Autowired
    private AssemblyService assemblyService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Create new Assembly.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Associate.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = String.class),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR, response = String.class)
    })
    public Assembly createAssembly(@Valid @RequestBody AssemblyDto assemblyDto) {
        return assemblyService.createAssembly(assemblyDto);
    }

    @PostMapping("/openVotingSession")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Open assembly voting session.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Associate.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = String.class),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR, response = String.class)
    })
    public Assembly openAssemblyAssemblySession(@Valid @RequestBody VotingSessionDto votingSessionDto) {
        return assemblyService.openAssemblyVotingSession(votingSessionDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve all assemblies registry on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = String.class),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR, response = String.class)
    })
    public Iterable<Assembly> getAllAssemblies() {
        return assemblyService.getAllAssemblies();
    }

    @PostMapping("/vote")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Vote in an assembly.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = String.class),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR, response = String.class)
    })
    public void vote(@Valid @RequestBody VoteDto voteDto) {
        assemblyService.vote(voteDto);
    }

    @GetMapping(value = "/{associateCpf}/{assemblyName}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve assembly registry by name on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Assembly.class),
            @ApiResponse(code = 400, message = BAD_REQUEST, response = String.class),
            @ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR, response = String.class)
    })
    public Assembly getAssemblyByName(@PathVariable String associateCpf, @PathVariable String assemblyName) {
        return assemblyService.findAssemblyByName(assemblyName, associateCpf);
    }
}
