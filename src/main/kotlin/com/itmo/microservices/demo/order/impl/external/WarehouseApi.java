package com.itmo.microservices.demo.order.impl.external;

import com.itmo.microservices.demo.order.api.dto.OrderDto;
import com.itmo.microservices.demo.order.api.external.IWarehouseApi;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WarehouseApi implements IWarehouseApi {

    private static final String API_URL = "http://77.234.215.138:30019/api/warehouse/book";

    @Override
    public ResponseEntity<String> book(OrderDto orderDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Map<String, OrderDto> parameters = new HashMap<>();
        parameters.put("order", orderDto);

        HttpEntity<Map<String, OrderDto>> request = new HttpEntity<>(parameters, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
        return response;
    }
}
