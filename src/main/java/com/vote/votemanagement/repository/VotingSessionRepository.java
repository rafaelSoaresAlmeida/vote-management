package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.VotingSession;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotingSessionRepository extends CrudRepository<VotingSession, Long> {
    List<VotingSession> findByOpened(final boolean opened);
}
