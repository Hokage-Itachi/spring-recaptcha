package com.example.recaptchaservice.controller;

import com.example.recaptchaservice.annotation.VerifyRecaptcha;
import com.example.recaptchaservice.service.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecaptchaController {
    @Autowired
    private RecaptchaService recaptchaService;

    @PostMapping("/api/login")
    @VerifyRecaptcha(
            action = "login",
            threshold = 0.5f
    )
    public ResponseEntity<Object> formVerify() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/register")
    @VerifyRecaptcha(
            action = "register"
    )
    public ResponseEntity<Object> register() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
