package com.naivebayesclassifier.api.converter;

import com.naivebayesclassifier.api.dtos.ClassifierResponseDTO;
import com.naivebayesclassifier.api.enitites.ClassifierEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClassifierMapper {
    ClassifierResponseDTO toClassifierResponseDTO(ClassifierEntity classifier);
}
