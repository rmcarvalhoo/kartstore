package br.com.kartstore.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClientExceptionTest {
    @Test
    public void testConstructorWithException() {
        Exception innerException = new Exception("Inner exception message");
        ClientException clientException = new ClientException(innerException);
        assertEquals(innerException, clientException.getCause());
        assertNotNull(clientException.getMessage());
    }

    @Test
    public void testConstructorWithMessageAndCause() {
        String message = "Test message";
        Throwable cause = new Throwable("Test cause");
        ClientException clientException = new ClientException(message, cause);
        assertEquals(message, clientException.getMessage());
        assertEquals(cause, clientException.getCause());
    }

    @Test
    public void testConstructorWithMessage() {
        String message = "Test message";
        ClientException clientException = new ClientException(message);
        assertEquals(message, clientException.getMessage());
        assertNull(clientException.getCause());
    }

}
