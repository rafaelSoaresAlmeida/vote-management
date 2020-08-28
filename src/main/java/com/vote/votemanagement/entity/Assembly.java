package com.vote.votemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity(name = "Assembly")
@Table(name = "Assembly")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Assembly {

    @Id
    @GeneratedValue
    @Column
    @JsonIgnore
    private long id;

    @Column
    @JsonProperty("Nome da pauta")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "votingSessionId")
    @JsonManagedReference
    @JsonProperty("Sessão de votos")
    private VotingSession votingSession;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpf")
    @JsonManagedReference
    @JsonProperty("Criado por")
    private Associate createdBy;

    @Column
    @CreationTimestamp
    @JsonProperty("Criada em")
    private LocalDateTime creationDate;
}
