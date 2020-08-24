package com.vote.votemanagement.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class ExternalValidateCpfResponseDto implements Serializable {

    private String status;
}