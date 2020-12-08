package com.example.voucher.model.rest;

import com.example.voucher.model.entity.SpecialOffer;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CreateSpecialOfferResponse {

    private Long id;
    private String offerName;
    private double discountPercentage;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;

    public CreateSpecialOfferResponse() {
    }

    public CreateSpecialOfferResponse(SpecialOffer specialOffer) {
        this.id = specialOffer.getId();
        this.offerName = specialOffer.getOfferName();
        this.discountPercentage = specialOffer.getDiscountPercentage();
        this.expirationDate = specialOffer.getExpirationDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
