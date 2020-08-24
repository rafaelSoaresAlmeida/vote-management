package com.vote.votemanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Builder
@Entity(name = "Associate")
@Table(name = "Associate")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Associate {

    @Id
    @Column
    @JsonProperty("CPF")
    private String cpf;

    @Column
    @JsonProperty("Nome do associado")
    private String name;

    @Column
    @CreationTimestamp
    @JsonProperty("Criado em")
    private LocalDateTime creationDate;
}
