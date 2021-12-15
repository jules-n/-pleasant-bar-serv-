package com.jules.persistence;

import com.jules.models.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends MongoRepository<Sequence, Integer>, SequenceRepositoryCustom {
    Integer findFirstByOrderByCountAsc();
}
