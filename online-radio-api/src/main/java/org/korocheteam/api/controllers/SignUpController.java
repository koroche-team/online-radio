package org.korocheteam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.ValidationErrorDto;
import org.korocheteam.api.models.dtos.requests.SignUpRequest;
import org.korocheteam.api.models.dtos.responses.SignUpResponse;
import org.korocheteam.api.models.dtos.responses.ValidationExceptionResponse;
import org.korocheteam.api.services.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest request, BindingResult result) {
        //костыль потому что хендлер не отлавливает ошибку надо как-то фиксить
        if (result.hasErrors()) {
            List<ValidationErrorDto> validationErrorDtos = new ArrayList<>();

            result.getAllErrors().forEach(objectError -> {
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

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(signUpService.signUp(request));
    }
}
