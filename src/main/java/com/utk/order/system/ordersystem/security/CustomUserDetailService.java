package com.utk.order.system.ordersystem.security;

import com.utk.order.system.ordersystem.model.Product;
import com.utk.order.system.ordersystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    public CustomUserDetailService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            return this.orderRepository.findByEmailId(username).orElse(new Product(username, "", "", "", "", "USER_NOT_FOUND"));
        }
        catch(UsernameNotFoundException usernameNotFoundException) {
            throw new UsernameNotFoundException("User not found");
        }
        catch(Exception e) {
            throw e;
        }
    }
}
