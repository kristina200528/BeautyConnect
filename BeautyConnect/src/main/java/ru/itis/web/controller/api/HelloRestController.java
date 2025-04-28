package ru.itis.web.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/hello")
public class HelloRestController {

    @GetMapping
    @Operation(description = "Привет тебе")
    public HelloDto helloDto() {
        return new HelloDto("Hello World!!!");
    }

    @Data
    @AllArgsConstructor
    public static class HelloDto {
        private String message;
    }

}
