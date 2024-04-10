package com.naivebayesclassifier.api.repositories;

import com.naivebayesclassifier.api.dtos.ClassifierStatusResponseDTO;
import com.naivebayesclassifier.api.enitites.ClassifierEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassifierRepository extends MongoRepository<ClassifierEntity, String> {
    @Query("{ 'id' : ?0 }")
    Optional<ClassifierEntity> findById(String id);

    @Query(value = "{ 'id' : ?0 }", fields = "{ 'status' : 1 }")
    Optional<ClassifierEntity> findStatusById(String id);

    ClassifierEntity save(ClassifierEntity entity);
}
