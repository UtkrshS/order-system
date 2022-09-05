package com.utk.order.system.ordersystem.transport;

import com.utk.order.system.ordersystem.model.Product;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.utk.order.system.ordersystem.config.Constants.PROCESSED;
import static com.utk.order.system.ordersystem.config.Constants.TOPIC;

@Component
public class KafkaOrderConsumer {
    private final Logger LOG = LoggerFactory.getLogger(KafkaOrderConsumer.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @KafkaListener(topics = TOPIC)
    public void listenToTopic(final ConsumerRecord<String, Product> record) throws InterruptedException {
        Thread.sleep(5000);
        Product product = record.value();
        LOG.info("Consuming message from topic: {}", product);
        Product updateOrder = Product.builder()
                .name(product.getName())
                .password(product.getPassword())
                .emailId(product.getEmailId())
                .orderId(product.getOrderId())
                .productName(product.getProductName())
                .orderStatus(PROCESSED)
                .build();
        this.mongoTemplate.save(updateOrder);
        LOG.info("Product is processed successfully: {}", updateOrder);
    }
}
