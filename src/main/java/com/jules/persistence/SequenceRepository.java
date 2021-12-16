package com.jules.persistence;

import com.jules.models.Sequence;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends MongoRepository<Sequence, Integer>, SequenceRepositoryCustom {
    @Aggregation(pipeline = { "{$group: { _id: '', total: {$max: $count }}}" })
    long findMaxByCount();
}
