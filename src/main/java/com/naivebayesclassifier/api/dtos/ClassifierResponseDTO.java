package com.naivebayesclassifier.api.dtos;

import java.util.List;

public record ClassifierResponseDTO(
        String id,
        String name,
        String description,
        String type,
        int size,
        String format,
        double accuracy,
        String status,
        double rating,
        String path,
        boolean isPublic,
        List<String> owners
) {}
