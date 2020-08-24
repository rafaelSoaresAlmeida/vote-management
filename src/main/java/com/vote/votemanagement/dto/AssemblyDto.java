package com.vote.votemanagement.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class AssemblyDto implements Serializable {

    private String name;

    private String associateCpf;
}