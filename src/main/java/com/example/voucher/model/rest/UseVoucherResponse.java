package com.example.voucher.model.rest;

import com.example.voucher.model.entity.Voucher;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UseVoucherResponse {
    private String voucherCode;
    private String email;
    private double discountPercentage;
    private boolean isUse;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate usageDate;

    public UseVoucherResponse() {
    }

    public UseVoucherResponse(String voucherCode, String email, double discountPercentage, boolean isUse, LocalDate usageDate) {
        this.voucherCode = voucherCode;
        this.email = email;
        this.discountPercentage = discountPercentage;
        this.isUse = isUse;
        this.usageDate = usageDate;
    }

    public UseVoucherResponse(Voucher voucher) {
        this.voucherCode = voucher.getVoucherCode();
        this.isUse = voucher.isUsed();
        this.usageDate = voucher.getUsageDate();
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean isUse) {
        isUse = isUse;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }
}
