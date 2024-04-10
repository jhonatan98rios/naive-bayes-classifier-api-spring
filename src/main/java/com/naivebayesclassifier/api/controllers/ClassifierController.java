package com.naivebayesclassifier.api.controllers;

import com.naivebayesclassifier.api.dtos.ClassifierResponseDTO;
import com.naivebayesclassifier.api.dtos.ClassifierStatusResponseDTO;
import com.naivebayesclassifier.api.dtos.CreateClassifierRequest;
import com.naivebayesclassifier.api.dtos.UploadClassifierResponse;
import com.naivebayesclassifier.api.enitites.ClassifierEntity;
import com.naivebayesclassifier.api.services.ClassifierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ClassifierController {

    private final ClassifierService classifierService;

    @GetMapping("/list-classifiers")
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

    @GetMapping("/read-classifier/{id}/status")
    public ResponseEntity<ClassifierStatusResponseDTO> getClassifierStatusById(@PathVariable String id) {
        ClassifierStatusResponseDTO classifierStatus = classifierService.getClassifierStatusByID(id);
        if (classifierStatus != null) {
            return ResponseEntity.ok(classifierStatus);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<UploadClassifierResponse> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("filename") String filename) {

        System.out.println("\nFilename:");
        System.out.println(filename);
        System.out.println("\nFile:");
        System.out.println(file);

        UploadClassifierResponse res = classifierService.uploadClassifierFile(file, filename);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/publish")
    public ResponseEntity<String> createClassifier(@RequestBody CreateClassifierRequest request) {
        System.out.println(request);
        String res = classifierService.createClassifier(request);
        return  ResponseEntity.ok("Value Received: " + res);
    }
}
