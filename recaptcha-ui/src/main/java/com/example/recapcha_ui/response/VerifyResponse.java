package com.example.recapcha_ui.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyResponse {
    private String timestamp;
    private String status;
    private String message;
    private String[] errors;

    @Override
    public String toString() {
        return "VerifyResponse\n{" +
                "\ttimestamp='" + timestamp + "',\n" +
                "\tstatus='" + status + "',\n" +
                "\tmessage='" + message + "',\n" +
                "\terrors=" + Arrays.toString(errors) +
                "\n}";
    }
}
