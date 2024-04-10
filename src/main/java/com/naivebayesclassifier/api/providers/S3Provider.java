package com.naivebayesclassifier.api.providers;

import java.io.IOException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class S3Provider {

    @Autowired
    private AmazonS3 amazonS3Client;

    @Value("${aws.bucketName}")
    private String bucketName;

    public String uploadFile(MultipartFile file, String objectKey) {
        try {
            // Configura os metadados do arquivo
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            // Cria a solicitação de upload e realiza o upload do arquivo para o S3
            PutObjectRequest request = new PutObjectRequest(bucketName, objectKey, file.getInputStream(), metadata);
            amazonS3Client.putObject(request);

            return objectKey;

        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao processar o upload do arquivo: " + e.getMessage();
        }
    }
}