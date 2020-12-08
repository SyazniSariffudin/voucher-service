package com.example.voucher.negative.service;

import com.example.voucher.exception.BusinessValidationException;
import com.example.voucher.model.entity.Recipient;
import com.example.voucher.model.rest.CreateRecipientRequest;
import com.example.voucher.respository.RecipientRepository;
import com.example.voucher.service.impl.RecipientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipientServiceTests {

    @Mock
    private RecipientRepository recipientRepository;

    @InjectMocks
    private RecipientServiceImpl recipientService;

    @Test
    public void givenExistingEmail_whenCreateRecipient_thenThrowBusinessValidationException(){

        when(recipientRepository.findByEmail(anyString())).thenReturn(new Recipient("John", "John@gmail.com"));

        try{
            recipientService.createRecipient(new CreateRecipientRequest("John", "John@gmail.com"));
        }catch (BusinessValidationException ex){
            assertThat(ex.getMessage()).isNotNull();
        }

        verify(recipientRepository, times(0)).save(any(Recipient.class));
    }
}
