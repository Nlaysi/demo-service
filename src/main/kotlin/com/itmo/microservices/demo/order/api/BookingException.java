package com.itmo.microservices.demo.order.api;

public class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}
