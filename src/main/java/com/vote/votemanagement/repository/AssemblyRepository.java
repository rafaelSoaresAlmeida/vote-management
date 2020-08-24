package com.vote.votemanagement.repository;

import com.vote.votemanagement.entity.Assembly;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssemblyRepository extends CrudRepository<Assembly, Long> {
    Assembly findByName(final String name);
}
