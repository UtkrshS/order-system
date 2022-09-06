package com.utk.order.system.ordersystem.security;

import com.utk.order.system.ordersystem.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

public class CustomerUserDetailServiceTest {

    private OrderRepository orderRepository;
    private CustomUserDetailService customUserDetailService;

    @Before
    public void setup() {
        orderRepository = Mockito.mock(OrderRepository.class);
        customUserDetailService = new CustomUserDetailService(orderRepository);
    }

    @Test
    public void testCallToFindBy() {
        customUserDetailService.loadUserByUsername(any());
        Mockito.verify(orderRepository, Mockito.times(1)).findByEmailId(any());
    }

}
