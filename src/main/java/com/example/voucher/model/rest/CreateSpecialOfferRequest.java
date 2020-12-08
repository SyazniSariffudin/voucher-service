package com.example.voucher.model.rest;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CreateSpecialOfferRequest {

    @NotEmpty
    @Size(min = 1, max = 255, message = "Special offer name length should be between 1 and 255")
    private String specialOfferName;

    @DecimalMax(value = "100.00")
    @DecimalMin(value = "0.00")
    private double discountPercentage;

    @NotEmpty
    private String expirationDate;

    public CreateSpecialOfferRequest() {
    }

    public CreateSpecialOfferRequest(
            @NotEmpty @Size(min = 1, max = 255, message = "Special offer name length should be between 1 and 255") String specialOfferName
            , @DecimalMax(value = "100.00") @DecimalMin(value = "0.00") double discountPercentage
            , @NotEmpty String expirationDate) {
        this.specialOfferName = specialOfferName;
        this.discountPercentage = discountPercentage;
        this.expirationDate = expirationDate;
    }

    public String getSpecialOfferName() {
        return specialOfferName;
    }

    public void setSpecialOfferName(String specialOfferName) {
        this.specialOfferName = specialOfferName;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
