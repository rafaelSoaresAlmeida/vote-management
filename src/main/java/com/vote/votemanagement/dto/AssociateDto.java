package com.vote.votemanagement.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class AssociateDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String cpf;
    private String name;
    private String createdByAssociateCpf;
}