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
        return "GoogleResponse\n{" +
                "\tsuccess=" + success +
                ", \n\thostname='" + hostname +
                ", \n\taction='" + action +
                ", \n\tscore=" + score +
                ", \n\tchallenge_ts='" + challenge_ts +
                "\n}";
    }
}
