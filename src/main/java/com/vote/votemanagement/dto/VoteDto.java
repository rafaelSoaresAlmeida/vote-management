package com.vote.votemanagement.dto;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Type;

import java.io.Serializable;

@Builder
@Getter
public class VoteDto implements Serializable {

    private String assemblyName;

    private String vote;

    private String associateCpf;
}