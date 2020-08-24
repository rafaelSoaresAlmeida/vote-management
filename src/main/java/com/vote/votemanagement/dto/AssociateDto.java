package com.vote.votemanagement.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class AssociateDto implements Serializable {
    private String cpf;

    private String name;
}