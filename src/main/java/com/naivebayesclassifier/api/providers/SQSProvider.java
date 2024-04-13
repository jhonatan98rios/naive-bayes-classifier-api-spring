package com.naivebayesclassifier.api.providers;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.naivebayesclassifier.api.enitites.EventPayload;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;


@Component
public class SQSProvider {

    @Value("${aws.numQueueUrl}")
    private String numQueueUrl;

    @Value("${aws.nlpQueueUrl}")
    private String nlpQueueUrl;

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public SQSProvider(AmazonSQSAsync amazonSqs) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
    }

    public boolean sendMessage(final EventPayload messagePayload) {

        try {
            // Converter o messagePayload em JSON e cria a mensagem
            String jsonPayload = objectMapper.writeValueAsString(messagePayload);
            Message<String> msg = MessageBuilder.withPayload(jsonPayload)
                    .setHeader("sender", "naive-bayes-classifier-api")
                    .build();

            // Seleciona a fila com base no tipo de Classifier
            String queueUrl = messagePayload.type().equals("nlp") ? nlpQueueUrl : numQueueUrl;

            // Enviar a mensagem para a fila SQS
            queueMessagingTemplate.send(queueUrl, msg);
            return true;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
