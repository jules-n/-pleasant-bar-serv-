package com.jules.kafka;

import lombok.Setter;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Setter(onMethod_ = @Autowired)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value("${cloudkarafka.topic}")
    private String topic;


    public void send(String message) {
       throw new NotImplementedException();
    }

}