package com.vote.votemanagement.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class VoteDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String assemblyName;
    private String vote;
    private String associateCpf;
}