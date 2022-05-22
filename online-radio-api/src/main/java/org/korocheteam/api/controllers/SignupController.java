package org.korocheteam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.models.dtos.SignupRequest;
import org.korocheteam.api.models.dtos.SignupResponse;
import org.korocheteam.api.services.SignupService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {
    private final SignupService signupService;

    @PostMapping
    @ResponseBody
    public SignupResponse signup(@RequestBody SignupRequest request) {
        return signupService.signUp(request);
    }
}
