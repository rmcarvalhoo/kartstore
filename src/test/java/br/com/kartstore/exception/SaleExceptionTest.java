package br.com.kartstore.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaleExceptionTest {

    @Test
    public void testConstructorWithException() {
        Exception innerException = new Exception("Inner exception message");
        SaleException saleException = new SaleException(innerException);
        assertEquals(innerException, saleException.getCause());
        assertNotNull(saleException.getMessage());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Test message";
        Throwable cause = new Throwable("Test cause");
        SaleException saleException = new SaleException(message, cause);
        assertEquals(message, saleException.getMessage());
        assertEquals(cause, saleException.getCause());
    }

    @Test
    public void testConstructorWithMessage() {
        String message = "Test message";
        SaleException saleException = new SaleException(message);
        assertEquals(message, saleException.getMessage());
        assertNull(saleException.getCause());
    }
}
