package com.vote.votemanagement.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity(name = "Vote")
@Table(name = "Vote")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vote {

    @Id
    @GeneratedValue
    @Column
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cpf")
    @JsonManagedReference
    private Associate associate;

    @Column
    private Boolean value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "votingSession_id")
    private VotingSession votingSession;

    @Column
    @CreationTimestamp
    private LocalDateTime createdDate;
}
