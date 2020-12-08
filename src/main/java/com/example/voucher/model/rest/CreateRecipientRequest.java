package com.example.voucher.model.rest;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CreateRecipientRequest {

    @NotEmpty
    @Size(min = 1, max = 255, message = "Recipient name length should be between 1 and 255")
    private String name;

    @Email
    @Size(min = 1, max = 255, message = "Email length should be between 1 and 255")
    private String email;

    public CreateRecipientRequest() {
    }

    public CreateRecipientRequest(
            @NotEmpty @Size(min = 1, max = 255, message = "Recipient name length should be between 1 and 255") String name,
            @Email @Size(min = 1, max = 255, message = "Email length should be between 1 and 255") String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
