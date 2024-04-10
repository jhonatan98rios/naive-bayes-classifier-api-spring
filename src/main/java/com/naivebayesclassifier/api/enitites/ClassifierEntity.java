package com.naivebayesclassifier.api.enitites;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "classifiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassifierEntity {
    @Id
    private String _id;

    private String id;
    private String name;
    private String description;
    private String type;
    private long size;
    private String format;
    private float accuracy;
    private String status;
    private float rating;
    private String path;
    private boolean isPublic;
    private List<String> owners;

    public ClassifierEntity(
            String id,
            String name,
            String description,
            String type,
            long size,
            String format,
            String status,
            String path,
            boolean isPublic,
            List<String> owners
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.size = size;
        this.format = format;
        this.accuracy = 0f;
        this.status = status;
        this.rating = 0f;
        this.path = path;
        this.isPublic = isPublic;
        this.owners = owners;
    }
}
