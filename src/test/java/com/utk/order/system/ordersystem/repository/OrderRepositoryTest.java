package com.utk.order.system.ordersystem.repository;

import com.utk.order.system.ordersystem.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testFindByEmailId() {
        Product product = Product.builder()
                .name("abc")
                .password("abc")
                .productName("abc")
                .emailId("abc@abc" + Math.random())
                .orderStatus("PLACED")
                .build();
        this.orderRepository.save(product);
        Optional<Product> repoProduct = this.orderRepository.findByEmailId(product.getEmailId());
        assertThat(repoProduct.get().getName()).isEqualTo(product.getName());
    }
}
