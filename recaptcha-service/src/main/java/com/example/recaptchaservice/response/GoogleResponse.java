package com.example.recaptchaservice.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleResponse {
    private boolean success;
    private String hostname;
    private String action;
    private Float score;
    private String challenge_ts;
    @JsonProperty("error-codes")
    private String[] errorCodes;

    @Override
    public String toString() {
        return "GoogleResponse{" +
                "success=" + success +
                ", hostname='" + hostname + '\'' +
                ", action='" + action + '\'' +
                ", score=" + score +
                ", challenge_ts='" + challenge_ts + '\'' +
                '}';
    }
}
