package com.react.mobile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class MobileController {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of(
                "message",
                "Hello from Spring Boot (Mobile API)"
        );
    }
}