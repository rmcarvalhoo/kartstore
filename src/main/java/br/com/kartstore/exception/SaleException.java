package br.com.kartstore.exception;

public class SaleException extends RuntimeException{
    public SaleException(Exception e) {
        super(e);
    }

    public SaleException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaleException(String message) {
        super(message);
    }}
