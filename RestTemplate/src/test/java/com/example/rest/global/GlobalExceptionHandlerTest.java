package com.example.rest.global;

import com.example.rest.exception.ConsumerClientException;
import com.example.rest.exception.ErrorDetail;
import com.example.rest.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import java.net.ConnectException;

import static org.mockito.Mockito.when;

public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler sut;

    @Mock
    WebRequest webRequest;


    static final String REQUEST_PATH = "/some/path";

    static final String GLOBAL = "SOMETHING WENT WRONG";

    static final String CLIENT = "Client exception";

    static final String CONNECTION = "Connection exception";

    static final String ILLEGAL = "Illegal exception";
    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        sut = new GlobalExceptionHandler();
        when(webRequest.getContextPath()).thenReturn(REQUEST_PATH);
    }

    @Test
    void consumerClient(){
        ConsumerClientException consumerClientException = new ConsumerClientException(CLIENT);
        ResponseEntity<ErrorDetail> responseEntity = sut.consumerClientExp(consumerClientException,webRequest);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getStatusCode());
    }

    @Test
    void connectException(){
        ConnectException connectException = new ConnectException(CONNECTION);
        ResponseEntity<ErrorDetail> responseEntity = sut.connectionException(connectException,webRequest);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getStatusCode());
    }

    @Test
    void illegalException(){
        IllegalStateException illegalStateException = new IllegalStateException(ILLEGAL);
        ResponseEntity<ErrorDetail> responseEntity = sut.illegalStateException(illegalStateException,webRequest);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getStatusCode());
    }

    @Test
    void globalException(){
        Exception exception = new ConsumerClientException(CLIENT);
        ResponseEntity<ErrorDetail> responseEntity = sut.handleGlobalException(exception,webRequest);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertNotNull(responseEntity.getStatusCode());
    }




}
