package com.Nest.Icu.exceptions;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ICUGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	// @ExceptionHandler(BedNotFoundException.class)
	// public ResponseEntity<String> BedNotFoundException(Throwable throwable){
	// 	return new ResponseEntity<String>(throwable.getMessage(), HttpStatus.NOT_FOUND);
	// }
}
