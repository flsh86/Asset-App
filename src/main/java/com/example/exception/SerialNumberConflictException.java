package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, code = HttpStatus.CONFLICT, reason = "Wyposażenie z takim numerem seryjnym już istnieje")
public class SerialNumberConflictException extends RuntimeException{

    public SerialNumberConflictException() {

    }
}
