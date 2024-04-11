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


@Component
@RequiredArgsConstructor
public class SQSProvider {

    @Value("${aws.nlpQueueUrl}")
    private String queueName;

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    public SQSProvider(AmazonSQSAsync amazonSqs) {
        this.queueMessagingTemplate = new QueueMessagingTemplate(amazonSqs);
    }

    public boolean sendMessage(final EventPayload messagePayload) {
        Message<EventPayload> msg = MessageBuilder.withPayload(messagePayload)
                .setHeader("sender", "naive-bayes-classifier-api")
                .build();

        System.out.println(msg);

        // Enviar a mensagem para a fila SQS
        queueMessagingTemplate.send(queueName, msg);

        // Retornar verdadeiro se a mensagem foi enviada com sucesso
        return true;
    }
}
