package com.example.identityservice.Exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends RuntimeException {
    private String message;

    public NotFoundException(String msg) {
        super(msg);
        this.message = msg;
    }
}
