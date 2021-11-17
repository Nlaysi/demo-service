package com.itmo.microservices.demo.order.impl.external;

import com.itmo.microservices.demo.order.api.dto.ItemQuantityRequestDTOJava;
import com.itmo.microservices.demo.order.api.dto.OrderDto;
import com.itmo.microservices.demo.order.api.external.IWarehouseApi;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.stream.Collectors;

public class WarehouseApi implements IWarehouseApi {

    private static final String API_URL_PREFIX = "http://77.234.215.138:30019/api/warehouse";

    @Override
    public ResponseEntity<Set> book(OrderDto orderDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(API_URL_PREFIX + "/book", prepareRequest(orderDto), Set.class);
    }

    @Override
    public ResponseEntity<String> unbook(OrderDto orderDto) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(API_URL_PREFIX + "/unbook", prepareRequest(orderDto), String.class);
    }

    private HttpEntity<Map<String, List<ItemQuantityRequestDTOJava>>> prepareRequest(OrderDto orderDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Map<String, List<ItemQuantityRequestDTOJava>> parameters = new HashMap<>();
        List<ItemQuantityRequestDTOJava> itemList = orderDto.getItemList()
                .stream()
                .map(orderItem ->
                        new ItemQuantityRequestDTOJava(orderItem.getCatalogItemId(),
                                orderItem.getAmount()))
                .collect(Collectors.toList());
        parameters.put("itemList", itemList);

        return new HttpEntity<>(parameters, headers);
    }
}
