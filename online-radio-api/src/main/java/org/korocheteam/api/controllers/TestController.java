package org.korocheteam.api.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("test controller")
@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "return test")
    @ApiResponse(code = 200, message = "return test")
    @GetMapping
    public ResponseEntity<Object> test() {
        return ResponseEntity.ok("Test");
    }
}
