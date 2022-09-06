package com.utk.order.system.ordersystem.transport;

import com.utk.order.system.ordersystem.model.Product;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.mockito.ArgumentMatchers.any;

public class KafkaOrderConsumerTest {

    private MongoTemplate mongoTemplate;
    private KafkaOrderConsumer kafkaOrderConsumer;

    @Before
    public void setup() {
        mongoTemplate = Mockito.mock(MongoTemplate.class);
        kafkaOrderConsumer = new KafkaOrderConsumer(mongoTemplate);
    }

    @Test
    public void testListenToTopic() {
        Product product = Product.builder()
                .name("abc")
                .password("abc")
                .productName("abc")
                .emailId("abc@abc" + Math.random())
                .orderStatus("PLACED")
                .build();
        ConsumerRecord<String, Product> record = new ConsumerRecord<>("ordersystem.1", 0, 123L, "key", product);
        kafkaOrderConsumer.listenToTopic(record);
        Assert.assertNotNull(record);
        Mockito.verify(mongoTemplate, Mockito.times(1)).save(any());
    }
}
