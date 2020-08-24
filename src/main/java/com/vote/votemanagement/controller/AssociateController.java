package com.vote.votemanagement.controller;

import com.vote.votemanagement.dto.AssociateDto;
import com.vote.votemanagement.entity.Associate;
import com.vote.votemanagement.service.AssociateService;
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
import java.util.ArrayList;

@RestController
@RequestMapping(path = "/associate", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "Associate service controller")
@Slf4j
@Controller
public class AssociateController {

    @Autowired
    private AssociateService associateService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Insert new associate.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = Associate.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public Associate createAssociate(@Valid @RequestBody AssociateDto associateDto) {
        return associateService.createAssociate(associateDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve all associates registry on database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ArrayList.class),
            @ApiResponse(code = 400, message = "Bad Request", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server ErrorMessage", response = String.class)
    })
    public Iterable<Associate> getAllAssociates() {
        return associateService.getAllAssociates();
    }
}
