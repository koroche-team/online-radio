package org.korocheteam.api.controllers;

import lombok.RequiredArgsConstructor;
import org.korocheteam.api.services.CoverService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/cover")
@RequiredArgsConstructor
public class CoverController {
    private final CoverService coverService;

    @GetMapping(value= "/{uuid}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public FileSystemResource getCover(@PathVariable("uuid") String uuid) {
        return new FileSystemResource(new File(coverService.getCoverByUuid(uuid).getPath()));
    }
}
