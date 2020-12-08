package com.example.voucher.model.rest;

import com.example.voucher.model.entity.Recipient;

public class CreateRecipientResponse {

    private Long id;
    private String name;
    private String email;

    public CreateRecipientResponse() {
    }

    public CreateRecipientResponse(Recipient recipient) {
        this.id = recipient.getId();
        this.name = recipient.getName();
        this.email = recipient.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
