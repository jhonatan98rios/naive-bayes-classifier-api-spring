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
        private int size;
        private String format;
        private float accuracy;
        private String status;
        private float rating;
        private String path;
        private boolean isPublic;
        private List<String> owners;
    }
