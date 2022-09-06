package com.utk.order.system.ordersystem.controller;

import com.utk.order.system.ordersystem.model.Product;
import com.utk.order.system.ordersystem.repository.OrderRepository;
import com.utk.order.system.ordersystem.transport.KafkaOrderProducer;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RetailOrderController.class)
public class RetailOrderControllerTest {

    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    private RetailOrderController retailOrderController;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private KafkaOrderProducer kafkaOrderProducer;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        retailOrderController = new RetailOrderController(kafkaOrderProducer, orderRepository, passwordEncoder);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        String uri = "/allproducts";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.ALL_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void testGetOrderStatusById() throws Exception {
        Product product = Product.builder()
                .name("abc")
                .password("abc")
                .productName("abc")
                .emailId("abc@abc" + Math.random())
                .orderStatus("PLACED")
                .build();
        String uri = "/order/status/475718724928558";
        Mockito.when(this.orderRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(product));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.ALL_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void testProductDetailsById() throws Exception {
        Product product = Product.builder()
                .name("abc")
                .password("abc")
                .productName("abc")
                .emailId("abc@abc" + Math.random())
                .orderStatus("PLACED")
                .build();
        String uri = "/order/475718724928558";
        Mockito.when(this.orderRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(product));
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.ALL_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    public void testPlaceNewOrder() throws Exception {
        Product product = Product.builder()
                .name("abc")
                .password("abc")
                .productName("abc")
                .emailId("abc@abc" + Math.random())
                .orderStatus("PLACED")
                .build();
        JSONObject jsonData = new JSONObject(loadFileAsString("src/test/resources/sample.json"));
        String uri = "/placeOrder";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE).content(jsonData.toString())).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

    }

    private String loadFileAsString(final String filepath) {
        StringBuilder stringBuilder = new StringBuilder();
        try (Stream<String> stringStream = Files.lines(Paths.get(filepath))) {
            stringStream.forEach(s -> stringBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
