package com.alkemy.ong.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidUserException extends Exception {
    public InvalidUserException(String message) {
        super(message);
    }
}
