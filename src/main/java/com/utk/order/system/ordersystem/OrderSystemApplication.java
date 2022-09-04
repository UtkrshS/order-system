package com.utk.order.system.ordersystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderSystemApplication {
    private static final Logger LOG = LoggerFactory.getLogger(OrderSystemApplication.class);
    public static void main(String[] args) {
        LOG.info("************ ORDER SERVICE STARTS *****************");
        SpringApplication.run(OrderSystemApplication.class, args);
    }

}
