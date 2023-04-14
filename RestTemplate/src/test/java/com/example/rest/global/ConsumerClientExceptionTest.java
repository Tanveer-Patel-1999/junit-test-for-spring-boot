package com.example.rest.global;

import com.example.rest.exception.ConsumerClientException;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ConsumerClientExceptionTest {

    private static final String EXCEPTION_MESSAGE = "Exception";

    private static final String IO_EXCEPTION_MESSAGE = "IO!";

    @Test
    public void testMessageConstructor() {
        ConsumerClientException exception = new ConsumerClientException(EXCEPTION_MESSAGE);
        assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void testMessageAndCauseConstructor() {
        IOException cause = new IOException(IO_EXCEPTION_MESSAGE);

        ConsumerClientException exception = new ConsumerClientException(EXCEPTION_MESSAGE, cause);
        assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
        assertThat(exception.getCause(), instanceOf(IOException.class));
        assertEquals(IO_EXCEPTION_MESSAGE, exception.getCause().getMessage());
    }

    @Test
    public void testCauseConstructor() {
        IOException cause = new IOException(IO_EXCEPTION_MESSAGE);

        ConsumerClientException exception = new ConsumerClientException(cause);

        assertThat(exception.getCause(), instanceOf(IOException.class));
        assertEquals(IO_EXCEPTION_MESSAGE, exception.getCause().getMessage());
    }


}
