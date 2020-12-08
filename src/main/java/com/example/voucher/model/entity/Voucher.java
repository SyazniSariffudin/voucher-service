package com.example.voucher.model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "voucher")
public class Voucher{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voucher_code", unique = true)
    private String voucherCode;

    @ManyToOne
    private Recipient recipient;

    @ManyToOne
    private SpecialOffer specialOffer;

    @Column(name = "is_used", columnDefinition="tinyint(1) default 0")
    private boolean isUsed;

    @Column(name = "usage_date", columnDefinition = "DATE")
    private LocalDate usageDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public SpecialOffer getSpecialOffer() {
        return specialOffer;
    }

    public void setSpecialOffer(SpecialOffer specialOffer) {
        this.specialOffer = specialOffer;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public LocalDate getUsageDate() {
        return usageDate;
    }

    public void setUsageDate(LocalDate usageDate) {
        this.usageDate = usageDate;
    }
}
