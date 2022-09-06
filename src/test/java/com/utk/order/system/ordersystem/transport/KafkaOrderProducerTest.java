package com.utk.order.system.ordersystem.transport;

import com.utk.order.system.ordersystem.model.Product;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;

public class KafkaOrderProducerTest {
    private KafkaTemplate kafkaTemplate;
    private KafkaOrderProducer kafkaOrderProducer;

    @Before
    public void setup() {
        kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        kafkaOrderProducer = new KafkaOrderProducer(kafkaTemplate);
    }

    @Test
    public void testSendToTopic() {
        Product product = Product.builder()
                .name("abc")
                .password("abc")
                .productName("abc")
                .emailId("abc@abc" + Math.random())
                .orderStatus("PLACED")
                .build();
        kafkaOrderProducer.sendProductToTopic(product);
        Mockito.verify(kafkaTemplate, Mockito.times(1)).send((ProducerRecord) any());
    }
}

