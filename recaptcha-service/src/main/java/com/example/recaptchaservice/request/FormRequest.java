package com.example.recaptchaservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormRequest {
    private String email;
    private String password;
    @JsonProperty("g-recaptcha-response")
    private String googleRecaptchaResponse;
}
