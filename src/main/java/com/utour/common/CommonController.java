package com.utour.common;

import com.utour.dto.ResultDto;
import com.utour.exception.ValidException;
import com.utour.service.LoginService;
import com.utour.util.ErrorUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.validation.Validator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

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

	protected ResponseEntity<Object> download(String path) throws IOException {
		return this.download(Paths.get(path));
	}

	protected ResponseEntity<Object> download(Path path) throws IOException {
		if(Objects.isNull(path) || !Files.exists(path) || !Files.isRegularFile(path)) {
			// throw new IOException("Cannot find");
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		String fileName = URLEncoder.encode(path.toFile().getName(), "UTF-8");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
		//headers.add("name", path.toFile().getName());
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}

	protected void validator(Object value) {
		BindingResult bindingResult = new BeanPropertyBindingResult(value, value.getClass().getName());
		this.validator.validate(value, bindingResult);
		if(bindingResult.hasErrors()) {
			throw new ValidException(bindingResult);
		}
	}

	/**
	 * @param filePath
	 * @return
	 */
	protected ResponseEntity<?> getImageResponseEntity(Path filePath) {

		try {
			Objects.requireNonNull(filePath);
			if(!Files.isRegularFile(filePath)) {
				throw new IOException();
			}

			// 파일이 검색된 영역임
			// 파일명 확장자에 따른 mediaType 지정
			String fileName = filePath.toFile().getName();
			String fileExt = FilenameUtils.getExtension(fileName);
			MediaType imageType = MediaType.parseMediaType("image/" + fileExt);

			try (InputStream inputStream = Files.newInputStream(filePath)){
				final HttpHeaders headers = new HttpHeaders();
				headers.setContentType(imageType);
				return ResponseEntity
						.status(HttpStatus.OK)
						.headers(headers)
						.body(IOUtils.toByteArray(inputStream));
			} catch (IOException ioException) {
				throw ioException;
			}

		} catch (Exception e) {

			log.error("{}", ErrorUtils.throwableInfo(e));

			ClassPathResource classPathResource = new ClassPathResource("/static/assets/images/no_image.jpg");
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			try (InputStream inputStream = classPathResource.getInputStream()){
				return ResponseEntity
						.status(HttpStatus.OK)
						.headers(headers)
						.body(IOUtils.toByteArray(inputStream));
			} catch (IOException ioException) {
				log.warn("{}", ErrorUtils.throwableInfo(ioException));
				return ResponseEntity
						.status(HttpStatus.OK)
						.headers(headers)
						.body(0);
			}
		}
	}
}
