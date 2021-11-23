package com.itmo.microservices.demo.order.impl.external;

import com.itmo.microservices.demo.order.api.dto.OrderDto;
import com.itmo.microservices.demo.order.api.external.IPaymentApi;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class PaymentApi implements IPaymentApi {
    private static final String API_URL = "http://77.234.215.138:30019/api/payment/transaction";

    @Override
    public void pay(OrderDto orderDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        Map<String, OrderDto> parameters = new HashMap<>();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        parameters.put("order", orderDto);

        HttpEntity<Map<String, OrderDto>> request = new HttpEntity<>(parameters, headers);

        restTemplate.postForEntity(API_URL, request, String.class);
    }
}
