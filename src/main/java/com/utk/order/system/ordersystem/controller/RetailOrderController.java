package com.utk.order.system.ordersystem.controller;

import com.utk.order.system.ordersystem.model.Product;
import com.utk.order.system.ordersystem.repository.OrderRepository;
import com.utk.order.system.ordersystem.transport.KafkaOrderProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;

import static com.utk.order.system.ordersystem.config.Constants.PLACED;

@RestController
@EnableWebMvc
public class RetailOrderController {
    private final Logger LOG = LoggerFactory.getLogger(RetailOrderController.class);

    public RetailOrderController(KafkaOrderProducer kafkaOrderProducer, OrderRepository orderRepository, PasswordEncoder passwordEncoder) {
        this.kafkaOrderProducer = kafkaOrderProducer;
        this.orderRepository = orderRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private KafkaOrderProducer kafkaOrderProducer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/order/{id}")
    public Optional<Product> getProductDetailsById(@PathVariable String id) {
        LOG.info("Getting product details by ID: {}", id);
        Optional<Product> product = this.orderRepository.findById(id);
        return product;
    }

    @GetMapping("/order/status/{id}")
    public String getOrderStatus(@PathVariable String id) {
        LOG.info("Getting product details by ID: {}", id);
        Optional<Product> product = this.orderRepository.findById(id);
        assert product.isPresent();
        return product.get().getOrderStatus();
    }

    @GetMapping("/allproducts")
    public List<Product> getAllProducts() {
        LOG.info("Getting all product details");
        List<Product> product = this.orderRepository.findAll();
        LOG.info("All product returned: {}", product);
        return product;
    }

    @PostMapping("/placeOrder")
    public ResponseEntity placeNewOrder(@RequestBody final Product product1) {
        LOG.info("Received new order request");
        try {
            Product product = Product.builder()
                    .orderId(String.valueOf(System.nanoTime() + product1.getProductName().hashCode()))
                    .name(product1.getName())
                    .password(passwordEncoder.encode(product1.getPassword()))
                    .emailId(product1.getEmailId())
                    .productName(product1.getProductName())
                    .orderStatus(PLACED)
                    .build();
            LOG.info("Placing new order request: {}", product);
            kafkaOrderProducer.sendProductToTopic(product);
            this.orderRepository.save(product);
            LOG.info("Order successfully booked!");
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            LOG.error("Could not place order: {}", e.getMessage());
            return (ResponseEntity) ResponseEntity.badRequest();
        }
    }
}

