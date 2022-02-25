package com.cogent.fooddeliveryapp.advice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cogent.fooddeliveryapp.exception.MyOwnException;
import com.cogent.fooddeliveryapp.exception.NameAlreadyExistsException;
import com.cogent.fooddeliveryapp.exception.NoDataFoundException;
import com.cogent.fooddeliveryapp.exception.apierror.ApiError;
import com.cogent.fooddeliveryapp.security.jwt.AuthEntryPointJwt;
import com.fasterxml.jackson.databind.ObjectMapper;

@org.springframework.web.bind.annotation.ControllerAdvice
// will handle all exceptions which are thrown by the controller/restcontroller
// using throws.
public class ControllerAdvice extends ResponseEntityExceptionHandler implements AuthenticationEntryPoint{
//	public class ControllerAdvice  implements AuthenticationEntryPoint{
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);
	
	
	@ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<?> noDataFoundException(NoDataFoundException e) {
		Map<String, String> map = new HashMap<>();
		map.put("message", "no data found");
		System.out.println(e);
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, e.getMessage(), e);
		System.out.println(apiError);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(NameAlreadyExistsException.class) // this is reponsible for handling NameAlreadyExistsException.
	public ResponseEntity<?> nameAlreadyExistsException(NameAlreadyExistsException e) {
		Map<String, String> map = new HashMap<>();
		map.put("message", "name already exists");
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "name already Exists", e);
		return buildResponseEntity(apiError);
	}

	@Override
//	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiError apiError = new ApiError(status);
		apiError.setMessage("Validation Error");
		apiError.addValidationErrors(ex.getFieldErrors());
		apiError.addValidationObjectErrors(ex.getBindingResult().getGlobalErrors());
		return buildResponseEntity(apiError);

	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {

		return new ResponseEntity<Object>(apiError, apiError.getStatus());

	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(e.getMessage());
		apiError.setDebugMessage(e.getRequiredType().getName());

		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(MyOwnException.class) // this is reponsible for handling NameAlreadyExistsException.
	public ResponseEntity<?> myOwnException(MyOwnException e) {
		Map<String, String> map = new HashMap<>();
		map.put("my own message", "this is my custom message ");
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "api error custom message ", e);
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(e.getMessage());
		apiError.setDebugMessage("check the type as integer");

		return buildResponseEntity(apiError);
	}
	//default one 
//	@ExceptionHandler(Exception.class)
//	protected ResponseEntity<?> handleException(Exception e) {
//
//		ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
//		apiError.setMessage(e.getMessage());
//
//		return buildResponseEntity(apiError);
//	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.error("Unauthorized error: {}", authException.getMessage());

	    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    // this response it is of json type.
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    // status code as unauthorized

	    final Map<String, Object> body = new HashMap<>();
	    body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
	    body.put("error", "Unauthorized");
	    body.put("message", authException.getMessage());
	    body.put("path", request.getServletPath());

	    final ObjectMapper mapper = new ObjectMapper();
	    mapper.writeValue(response.getOutputStream(), body);
		
	}
}
