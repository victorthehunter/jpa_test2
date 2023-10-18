package com.zakaria.jpa.generic;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseBuilder {

	private HttpStatus status;
	private String message;
	private Object data;
	private boolean success;
	
	public static ResponseEntity<ResponseBuilder> build(boolean success) {
		final ResponseBuilder responseBuilder = ResponseBuilder.builder()
				.success(success)
				.status(getStatusBySuccess(success))
				.message(getMessageBySuccess(success))
				.build();
		
		return new ResponseEntity<>(responseBuilder, responseBuilder.getStatus());
	}
	
	public static ResponseEntity<ResponseBuilder> build(HttpStatus status) {
		final boolean success = isSuccess(status);
		
		final ResponseBuilder responseBuilder = ResponseBuilder.builder()
				.success(success)
				.status(getStatusBySuccess(success))
				.message(getMessageBySuccess(success))
				.build();
		
		return new ResponseEntity<>(responseBuilder, responseBuilder.getStatus());
	}
	
	public static ResponseEntity<ResponseBuilder> build(boolean success, String message) {
		final ResponseBuilder responseBuilder = ResponseBuilder.builder()
				.success(success)
				.message(message)
				.status(getStatusBySuccess(success))
				.build();
		
		return new ResponseEntity<>(responseBuilder, responseBuilder.getStatus());
	}
	
	public static ResponseEntity<ResponseBuilder> build(boolean success, Object data) {
		final ResponseBuilder apiResponse = ResponseBuilder.builder()
				.data(data)
				.success(success)
				.status(getStatusBySuccess(success))
				.message(getMessageBySuccess(success))
				.build();
			
		return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
	}
	
	public static ResponseEntity<ResponseBuilder> build(boolean success, String message, Object data) {
		final ResponseBuilder apiResponse = ResponseBuilder.builder()
				.data(data)
				.success(success)
				.message(message)
				.status(getStatusBySuccess(success))
				.build();
			
		return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
	}

	public static ResponseEntity<ResponseBuilder> build(HttpStatus status, String message, Object data) {
		final ResponseBuilder apiResponse = ResponseBuilder.builder()
				.data(data)
				.status(status)
				.message(message)
				.success(isSuccess(status))
				.build();
			
		return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
	}

	public static ResponseEntity<ResponseBuilder> build(HttpStatus status, String message) {
		final ResponseBuilder apiResponse = ResponseBuilder.builder()
				.status(status)
				.message(message)
				.success(isSuccess(status))
				.build();
			
		return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
	}	
	
	public static ResponseEntity<ResponseBuilder> build(HttpStatus status, Object data) {
		final boolean success = isSuccess(status);
		
		final ResponseBuilder apiResponse = ResponseBuilder.builder()
				.data(data)
				.status(status)
				.success(success)
				.message(getMessageBySuccess(success))
				.build();
			
		return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
	}
	
	private static String getMessageBySuccess(boolean success) {
		return success ? "Success" : "Failed";
	}
	
	
	private static HttpStatus getStatusBySuccess(boolean success) {
		return success ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
	}
	
	private static boolean isSuccess(HttpStatus status) {
		return status == HttpStatus.OK || 
				status == HttpStatus.CREATED || 
				status == HttpStatus.ACCEPTED;
	}
}
