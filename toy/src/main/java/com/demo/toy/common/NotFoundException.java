package com.demo.toy.common;

public class NotFoundException  extends RuntimeException {
    public NotFoundException (String message) {
        super(message);
    }
}
