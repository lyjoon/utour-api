package com.utour.common;

import com.utour.transfer.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class CommonController extends CommonComponent {

	protected HttpSession getSession(boolean create){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(create);
	}

	protected HttpSession getSession(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);
	}

	protected <T> ResponseEntity<T> ok(T data){
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(data);
	}

	protected ResponseEntity<Void> ok(){
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).build();
	}

	protected ResponseEntity<Response> ok(String message){
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(Response.builder().message(message).build());
	}

	protected ResponseEntity<Void> response(HttpStatus httpStatus){
		return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).build();
	}

	protected ResponseEntity<Response> response(HttpStatus httpStatus, String message){
		return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(Response.builder().message(message).build());
	}

}
