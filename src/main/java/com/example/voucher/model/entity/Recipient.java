package com.example.voucher.model.entity;

import com.example.voucher.model.rest.CreateRecipientRequest;

import javax.persistence.*;

@Entity
@Table(name = "recipient")
public class Recipient{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    public Recipient() {
    }

    public Recipient(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Recipient(CreateRecipientRequest createRecipientRequest) {
        this.name = createRecipientRequest.getName();
        this.email = createRecipientRequest.getEmail();
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
