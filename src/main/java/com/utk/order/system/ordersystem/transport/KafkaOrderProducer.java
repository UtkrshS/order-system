package com.utk.order.system.ordersystem.transport;

import com.utk.order.system.ordersystem.model.Product;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.utk.order.system.ordersystem.config.Constants.TOPIC;

@Component
public class KafkaOrderProducer {
    private final Logger LOG = LoggerFactory.getLogger(KafkaOrderProducer.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendProductToTopic(final Product product) {
        LOG.info("Sending data to topic");
        try {
            kafkaTemplate.send(new ProducerRecord(TOPIC, product));
        }
        catch (Exception e) {
            LOG.error("Error occured while sending data to topic: {}", e.getMessage());
        }
    }


}
