package br.com.kartstore.exception;

public class ClientException extends RuntimeException{
    public ClientException(Exception e) {
        super(e);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientException(String message) {
        super(message);
    }}
