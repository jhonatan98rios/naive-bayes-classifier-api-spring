package com.naivebayesclassifier.api.controllers;

import com.naivebayesclassifier.api.dtos.ClassifierResponseDTO;
import com.naivebayesclassifier.api.enitites.ClassifierEntity;
import com.naivebayesclassifier.api.services.ClassifierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ClassifierController {

    private final ClassifierService classifierService;

    @GetMapping("/list-classifiers/")
    public ResponseEntity<List<ClassifierEntity>> getClassifiers() {
        List<ClassifierEntity> classifiers = classifierService.getClassifiers();
        return ResponseEntity.ok(classifiers);
    }

    @GetMapping("/read-classifier/{id}")
    public ResponseEntity<ClassifierResponseDTO> getClassifierById(@PathVariable String id) {
        ClassifierResponseDTO classifier = classifierService.getClassifierByID(id);
        if (classifier != null) {
            return ResponseEntity.ok(classifier);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
