package com.navulia.errorhandlingdemo.exception;

import com.navulia.errorhandlingdemo.dto.SupplierInputDTO;
import com.navulia.errorhandlingdemo.exeption.ErrorResponse;
import com.navulia.errorhandlingdemo.exeption.GlobalExceptionHandler;
import com.navulia.errorhandlingdemo.exeption.ResourceNotFoundException;
import com.navulia.errorhandlingdemo.exeption.SupplierAlreadyExistsException;
import com.navulia.errorhandlingdemo.service.SupplierService;
import com.navulia.errorhandlingdemo.utility.ErrorMessages;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class GlobalExceptionHandlerTest {
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    private ErrorResponse errorResponse;

    @Autowired
    private SupplierService supplierService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testHandleResourceNotFoundException(){
        // Create a sample ResourceNotFoundException
        ResourceNotFoundException ex=new ResourceNotFoundException(ErrorMessages.ERROR_SUPPLIER_NOT_FOUND + 12);
        // Define the expected response
        ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ex.getMessage()));
          //Define Actual response
        ResponseEntity<ErrorResponse> actualResponse = globalExceptionHandler.handleResourceNotFoundException(ex);
        // Call the method and assert the response
       assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
       ErrorResponse expectedResultBody = expectedResponse.getBody();
       ErrorResponse actualResultBody = actualResponse.getBody();
       if(expectedResultBody != null && actualResultBody !=null) {
           assertEquals(expectedResultBody.getMessage(), actualResultBody.getMessage());
       }
    }

    @Test
    public void testHandleSupplierExistsException(){
        // Create a sample ResourceNotFoundException
        SupplierAlreadyExistsException ex=new SupplierAlreadyExistsException(ErrorMessages.ERROR_SUPPLIER_ALREADY_FOUND + 10);
        // Define the expected response
        ResponseEntity<ErrorResponse> expectedResponse = ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(HttpStatus.CONFLICT.value(),
                ex.getMessage()));
        // Call the method and assert the response
        ResponseEntity<ErrorResponse> actualResponse = globalExceptionHandler.handleSupplierAlreadyExistsException(ex);
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        ErrorResponse expectedResultBody = expectedResponse.getBody();
        ErrorResponse actualResultBody = actualResponse.getBody();
        if(expectedResultBody != null && actualResultBody !=null) {
            assertEquals(expectedResultBody.getMessage(), actualResultBody.getMessage());
        }
    }


    @Test
    public void testHandleConstraintViolationException() {
       SupplierInputDTO supplier=new SupplierInputDTO("aa","Colombo",123);
        // Ensure that validation fails and throws ConstraintViolationException
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            supplierService.createSupplier(supplier);
        });
          ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleConstraintViolationException(exception);
          ErrorResponse actualResultBody = responseEntity.getBody();
          if(actualResultBody != null) {
              assertEquals("[Invalid Name: Must be of 4 - 30 characters]", responseEntity.getBody().getMessage());
          }
    }

   @Test
   public void testMethodArgumentTypeMismatchException(){
       MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
       when(ex.getMessage()).thenReturn("Given request param is not supported");
       // Call the method and assert the response
       ResponseEntity<ErrorResponse> actualResult = globalExceptionHandler.handleMethodArgumentTypeMismatchException(ex);
       assertEquals(HttpStatus.METHOD_NOT_ALLOWED, actualResult.getStatusCode());
       ErrorResponse actualResultBody = actualResult.getBody();
       if(actualResultBody != null) {
           assertEquals("Given request param is not supported", actualResult.getBody().getMessage());
       }
   }

   @Test
   public void testHttpRequestMethodNotSupportedException(){
       HttpRequestMethodNotSupportedException ex = mock(HttpRequestMethodNotSupportedException.class);
       when(ex.getMessage()).thenReturn("Request method is not supported");
       // Call the method and assert the response
       ResponseEntity<ErrorResponse> actualResult = globalExceptionHandler.handleRequestMethodNotSupportedException(ex);
       assertEquals(HttpStatus.METHOD_NOT_ALLOWED, actualResult.getStatusCode());
       ErrorResponse actualResultBody = actualResult.getBody();
       if(actualResultBody != null) {
           assertEquals("Request method is not supported", actualResult.getBody().getMessage());
       }
   }

    @Test
    public void testHttpMessageNotReadableException(){
        HttpMessageNotReadableException ex = mock(HttpMessageNotReadableException.class);
        when(ex.getMessage()).thenReturn("Could not read JSON");
        // Call the method and assert the response
        ResponseEntity<ErrorResponse> actualResult = globalExceptionHandler.handleHttpMessageNotReadableException(ex);
        assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());
        ErrorResponse actualResultBody = actualResult.getBody();
        if(actualResultBody != null) {
            assertEquals("Could not read JSON", actualResult.getBody().getMessage());
        }
    }
    @Test
    public void testNoHandlerFoundException(){
        NoHandlerFoundException ex = mock(NoHandlerFoundException.class);
        when(ex.getRequestURL()).thenReturn("Request URL is not found");
        // Call the method and assert the response
        ResponseEntity<ErrorResponse> actualResult = globalExceptionHandler.handleNoHandlerFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, actualResult.getStatusCode());
        ErrorResponse actualResultBody = actualResult.getBody();
        if(actualResultBody != null) {
            assertEquals("Request URL is not found", actualResult.getBody().getMessage());
        }
    }

    @Test
    public void testHttpMediaTypeNotSupportedException(){
        HttpMediaTypeNotSupportedException ex = mock(HttpMediaTypeNotSupportedException.class);
        when(ex.getContentType()).thenReturn(MediaType.valueOf("application/json"));
        // Call the method and assert the response
        ResponseEntity<ErrorResponse> actualResult = globalExceptionHandler.handleUnsupportedMediaTypeException(ex);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, actualResult.getStatusCode());
        ErrorResponse actualResultBody = actualResult.getBody();
        if(actualResultBody != null) {
            assertEquals("application/json", actualResult.getBody().getMessage());
        }
    }


}
