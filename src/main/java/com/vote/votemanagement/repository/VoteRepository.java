package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Vote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {
    Vote findByAssociateCpf(final String cpf);
}
