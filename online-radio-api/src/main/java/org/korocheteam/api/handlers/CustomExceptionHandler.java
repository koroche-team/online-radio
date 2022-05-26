package org.korocheteam.api.handlers;

import org.korocheteam.api.exceptions.AccountAlreadyExistsException;
import org.korocheteam.api.models.dtos.ValidationErrorDto;
import org.korocheteam.api.models.dtos.responses.ValidationExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(AccountAlreadyExistsException.class)
	public ResponseEntity<?> handleAccountAlreadyExists(AccountAlreadyExistsException exception) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(exception.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<ValidationErrorDto> validationErrorDtos = new ArrayList<>();

		e.getBindingResult().getAllErrors().forEach(objectError -> {
			String errorMessage = objectError.getDefaultMessage();
			ValidationErrorDto errorDto = ValidationErrorDto.builder()
					.message(errorMessage)
					.build();

			if (objectError instanceof FieldError) {
				String fieldName = ((FieldError) objectError).getField();
				errorDto.setField(fieldName);
			} else if (objectError instanceof ObjectError) {
				String objectName = objectError.getObjectName();
				errorDto.setObject(objectName);
			}
			validationErrorDtos.add(errorDto);
		});


		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ValidationExceptionResponse.builder()
						.errors(validationErrorDtos)
						.build());
	}


}
