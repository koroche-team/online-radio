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
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(signUpService.signUp(request));
    }
}
