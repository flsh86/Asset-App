package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, code = HttpStatus.CONFLICT, reason = "Użytkownik z takim peselem już istnieje")
public class PeselConflictException extends RuntimeException {

    public PeselConflictException() {

    }

}
