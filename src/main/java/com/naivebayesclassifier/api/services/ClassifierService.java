package com.naivebayesclassifier.api.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.naivebayesclassifier.api.dtos.CreateClassifierRequest;
import com.naivebayesclassifier.api.enitites.EventPayload;
import com.naivebayesclassifier.api.providers.SQSProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.naivebayesclassifier.api.providers.S3Provider;
import com.naivebayesclassifier.api.converter.ClassifierMapper;
import com.naivebayesclassifier.api.enitites.ClassifierEntity;
import com.naivebayesclassifier.api.exceptions.ServiceException;
import com.naivebayesclassifier.api.repositories.ClassifierRepository;

import com.naivebayesclassifier.api.dtos.UploadClassifierResponse;
import com.naivebayesclassifier.api.dtos.ClassifierStatusResponseDTO;
import com.naivebayesclassifier.api.dtos.ClassifierResponseDTO;


@Service
@RequiredArgsConstructor
public class ClassifierService {

    private final ClassifierRepository classifierRepository;
    private final ClassifierMapper classifierMapper;
    private final S3Provider s3Provider;
    private final SQSProvider sqsProvider;

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

    public ClassifierStatusResponseDTO getClassifierStatusByID(String id) {
        return classifierRepository.findStatusById(id)
                .map(classifierEntity -> new ClassifierStatusResponseDTO(classifierEntity.getStatus()))
                .orElse(null);
    }

    public UploadClassifierResponse uploadClassifierFile(MultipartFile file, String filename) {
        String uuid = UUID.randomUUID().toString();
        String objectKey = "raw/" + uuid + "-" + filename;
        String path = s3Provider.uploadFile(file, objectKey);
        return new UploadClassifierResponse(path, uuid);
    }

    public String createClassifier(CreateClassifierRequest request) {
        // Verifica se o ID já existe no banco
        Optional<ClassifierEntity> existingClassifierOptional = classifierRepository.findById(request.id());

        if (existingClassifierOptional.isPresent()) {
            // Se o ID já existe, não faz nada
            return "ID já existe";
        }

        // Se o ID não existe, cria um novo documento no banco de dados
        ClassifierEntity newClassifier = new ClassifierEntity(
                request.id(),
                request.name(),
                request.description(),
                request.type(),
                request.size(),
                request.format(),
                request.status(),
                request.path(),
                request.isPublic(),
                request.owners()
        );

        System.out.println(newClassifier);

        //classifierRepository.save(newClassifier);

        // Envia a mensagem para a fila SQS
        EventPayload payload = new EventPayload(
                request.id(),
                request.name(),
                request.description(),
                request.type(),
                request.format(),
                request.status(),
                request.path(),
                request.isPublic(),
                request.owners()
        );

        boolean sentMessage = sqsProvider.sendMessage(payload);

        if (!sentMessage) {
            throw new ServiceException("Failed to publish message on queue");
        }

        // Retorna o Id do objeto criado
        return newClassifier.getId();
    }
}
