package com.vote.votemanagement.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@Getter
public class AssemblyDto implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    private String name;
    @NotNull
    private String associateCpf;
}