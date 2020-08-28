package com.vote.votemanagement.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Getter
public class VoteDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    private String assemblyName;
    @NotNull
    private String vote;
    @NotNull
    private String associateCpf;
}