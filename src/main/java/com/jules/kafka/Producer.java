package com.jules.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Setter(onMethod_ = @Autowired)
    private KafkaTemplate<String, String> kafkaTemplate;


    @SneakyThrows
    public <T> void send(T data, String topic) {
        ObjectMapper objectMapper = new ObjectMapper();
        var message = objectMapper.writeValueAsString(data);
        kafkaTemplate.send(topic, message);
        kafkaTemplate.flush();
        kafkaTemplate.destroy();
       throw new NotImplementedException();
    }

}