package com.naivebayesclassifier.api.services;

import com.naivebayesclassifier.api.converter.ClassifierMapper;
import com.naivebayesclassifier.api.enitites.ClassifierEntity;
import com.naivebayesclassifier.api.exceptions.ServiceException;
import com.naivebayesclassifier.api.repositories.ClassifierRepository;
import com.naivebayesclassifier.api.dtos.ClassifierResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClassifierService {

    private final ClassifierRepository classifierRepository;
    private final ClassifierMapper classifierMapper;

    public List<ClassifierEntity> getClassifiers() {
        return classifierRepository.findAll();
    }

    public ClassifierResponseDTO getClassifierByID(String id) {
        try {
            Optional<ClassifierEntity> optionalClassifierEntity = classifierRepository.findById(id);

            if (optionalClassifierEntity.isPresent()) {
                ClassifierEntity classifierEntity = optionalClassifierEntity.get();
                return classifierMapper.toClassifierResponseDTO(classifierEntity);
            }

        } catch (Exception err) {
            throw new ServiceException("Read classifier by ID error: " + id, err);
        }

        throw new ResponseStatusException(NOT_FOUND, "Read classifier by ID error: " + id);
    }
}
