package com.naivebayesclassifier.api.enitites;

import java.util.List;

public record EventPayload(
        String id,
        String name,
        String description,
        String type,
        String format,
        String status,
        String path,
        boolean isPublic,
        List<String> owners)
{}