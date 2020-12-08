package com.example.voucher.model.entity;

import com.example.voucher.model.rest.CreateSpecialOfferRequest;
import com.example.voucher.util.DateUtil;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "special_offer")
public class SpecialOffer{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "offer_name")
    private String offerName;

    @Column(name = "discount_percentage")
    private double discountPercentage;

    @Column(name = "expiration_date", columnDefinition = "DATE")
    private LocalDate expirationDate;

    public SpecialOffer() {
    }

    public SpecialOffer(CreateSpecialOfferRequest createSpecialOfferRequest) {
        this.offerName = createSpecialOfferRequest.getSpecialOfferName();
        this.discountPercentage = createSpecialOfferRequest.getDiscountPercentage();
        this.expirationDate = DateUtil.convert(createSpecialOfferRequest.getExpirationDate());
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
