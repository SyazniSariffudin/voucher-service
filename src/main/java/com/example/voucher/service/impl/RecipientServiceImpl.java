package com.example.voucher.service.impl;

import com.example.voucher.exception.BusinessValidationException;
import com.example.voucher.model.entity.Recipient;
import com.example.voucher.model.rest.CreateRecipientRequest;
import com.example.voucher.model.rest.CreateRecipientResponse;
import com.example.voucher.respository.RecipientRepository;
import com.example.voucher.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class RecipientServiceImpl implements RecipientService {

    @Autowired
    RecipientRepository recipientRepository;

    @Override
    public ResponseEntity<CreateRecipientResponse> createRecipient(CreateRecipientRequest createRecipientRequest) {

        validateBeforeCreateRecipient(createRecipientRequest.getEmail());

        Recipient recipient = new Recipient(createRecipientRequest);
        Recipient savedRecipient = recipientRepository.save(recipient);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedRecipient.getId()).toUri();

        return ResponseEntity.created(location).body(new CreateRecipientResponse(savedRecipient));
    }

    public void validateBeforeCreateRecipient(String email){
        Recipient recipient = recipientRepository.findByEmail(email);

        if(recipient!=null){
            throw new BusinessValidationException("Email already exists. Please try using different email");
        }
    }

    @Override
    public Recipient getRecipientByEmail(String email) {
        return recipientRepository.findByEmail(email);
    }
}
