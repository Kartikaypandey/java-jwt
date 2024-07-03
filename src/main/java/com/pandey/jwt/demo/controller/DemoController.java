package com.pandey.jwt.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {



    @GetMapping()
    public ResponseEntity<String> getDemo(){
        return ResponseEntity.ok("Hello world");
    }
}
