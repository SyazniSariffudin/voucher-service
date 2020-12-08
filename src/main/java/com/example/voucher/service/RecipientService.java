package com.example.voucher.service;

import com.example.voucher.model.entity.Recipient;
import com.example.voucher.model.rest.CreateRecipientRequest;
import com.example.voucher.model.rest.CreateRecipientResponse;
import org.springframework.http.ResponseEntity;

public interface RecipientService {
    ResponseEntity<CreateRecipientResponse> createRecipient(CreateRecipientRequest createRecipientRequest);
    Recipient getRecipientByEmail(String email);
}
