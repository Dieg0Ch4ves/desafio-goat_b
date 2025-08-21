package com.diego.desafio_goat_b.exception;

public class TokenInvalidOrExpiredException extends RuntimeException {
    public TokenInvalidOrExpiredException(String message) {
        super(message);
    }
}
