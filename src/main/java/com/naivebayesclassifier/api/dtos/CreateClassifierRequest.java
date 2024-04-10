package com.naivebayesclassifier.api.dtos;

import java.util.List;

public record CreateClassifierRequest(
        String id,
        String name,
        String description,
        long size,
        String format,
        String type,
        String status,
        String path,
        boolean isPublic,
        List<String> owners
) {}
