package com.utour.common;

import com.utour.dto.ResultDto;
import com.utour.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CommonController extends CommonComponent {

	@Autowired
	protected Validator validator;

	protected HttpSession getSession(boolean create){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(create);
	}

	protected HttpSession getSession(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession(false);
	}

	protected <T> ResultDto ok(T data){
		return ResultDto.builder().result(data).build();
	}

	protected ResultDto<Void> ok(){
		return ResultDto.<Void>builder()
				.message(Constants.SUCCESS)
				.build();
	}

	protected ResultDto<Void> ok(String message){
		return ResultDto.<Void>builder().message(message).build();
	}

	protected ResultDto<String> ok(String message, String body){
		return ResultDto.<String>builder().message(message).result(body).build();
	}

	protected ResponseEntity<Void> response(HttpStatus httpStatus){
		return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).build();
	}

	protected ResponseEntity<ResultDto> response(HttpStatus httpStatus, String message){
		return ResponseEntity.status(httpStatus).contentType(MediaType.APPLICATION_JSON).body(ResultDto.builder().message(message).build());
	}

	protected Character useByToken(String authorization){
		return Optional.ofNullable(this.getBean(LoginService.class).isExpired(authorization))
				.map(v -> v ? null : Constants.Y).orElse(Constants.Y);
	}
}
