package com.intern.resource.base.exception;

import com.intern.resource.MSISResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(ApplicationErrorException.class)
	@ResponseStatus(value = HttpStatus.OK)
	public MSISResponse handleBizException(ApplicationErrorException e) {
		String msg = "app exception";
		if (e != null) {
			msg = e.getMsg();
			log.warn(e.toString());
		}
		return MSISResponse.fail(msg);
	}


}
