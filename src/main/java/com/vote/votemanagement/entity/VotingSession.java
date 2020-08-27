package com.vote.votemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity(name = "VotingSession")
@Table(name = "VotingSession")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VotingSession {

    @Id
    @GeneratedValue
    @Column
    @JsonIgnore
    private long id;

    @OneToMany(mappedBy = "votingSession", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Vote> votes;

    @Column
    @JsonProperty("Aberta")
    private boolean opened;

    @Column
    @CreationTimestamp
    @JsonProperty("Data de inicio")
    private LocalDateTime startDate;

    @Column
    @Timestamp
    @JsonProperty("Data de encerramento")
    private LocalDateTime finishDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpf")
    @JsonProperty("Aberta por")
    private Associate openedBy;

    @Column
    @JsonProperty("Total de votos SIM")
    private Long yes;

    @Column
    @JsonProperty("Total de votos NÃO")
    private Long no;
}
