package com.example.voucher.service;

import com.example.voucher.model.rest.CreateSpecialOfferRequest;
import com.example.voucher.model.rest.CreateSpecialOfferResponse;
import com.example.voucher.model.rest.GetVoucherByEmailResponse;
import com.example.voucher.model.rest.UseVoucherResponse;
import org.springframework.http.ResponseEntity;

public interface VoucherService {
    ResponseEntity<CreateSpecialOfferResponse> createSpecialOffer(CreateSpecialOfferRequest createSpecialOfferRequest);
    ResponseEntity<UseVoucherResponse> useVoucher(String voucherCode, String email);
    ResponseEntity<GetVoucherByEmailResponse> getVoucherByEmail(String email, int page, int pageSize);
}
