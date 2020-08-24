package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Associate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends CrudRepository<Associate, Long> {
    Associate findByCpf(final String cpf);
}
