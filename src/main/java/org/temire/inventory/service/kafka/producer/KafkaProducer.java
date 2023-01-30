package org.temire.inventory.service.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.temire.inventory.service.data.model.Order;

@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Order> kafkaTemplate;

    @Value("${kafka.producer.order.name}")
    String topicName;

    public void sendMessage(Order message){
        LOGGER.info(String.format("Message sent -> %s", message));
        kafkaTemplate.send(topicName, message);
    }
}