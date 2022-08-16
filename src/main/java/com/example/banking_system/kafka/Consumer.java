package com.example.banking_system.kafka;

import com.example.banking_system.repositories.CommaSeperatedAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class Consumer {
    @Autowired
    private CommaSeperatedAccountRepository fileProcess;

    @KafkaListener(topics = {"logs"}, groupId = "logs_group")
    public void listenTransfer(@Payload String message,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
    ){
        System.out.println("Message : " + message + ", Partition : " + partition);
        try {
            fileProcess.saveLog(message);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Log File save error. Log:" + message + ", Partition : " + partition);
        }
    }
}