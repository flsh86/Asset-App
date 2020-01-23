package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "Zapisywany obiekt nie może mieć ustawionego id")
public class IllegalIdException extends RuntimeException {
    public IllegalIdException() {
    }
}
