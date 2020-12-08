package com.example.voucher.positive.service;

import com.example.voucher.model.entity.Recipient;
import com.example.voucher.model.rest.CreateRecipientRequest;
import com.example.voucher.model.rest.CreateRecipientResponse;
import com.example.voucher.respository.RecipientRepository;
import com.example.voucher.service.impl.RecipientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

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

    @Mock
    HttpServletRequest request;

    @BeforeEach
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void givenValidRecipient_whenCreateRecipient_thenReturnCreated() throws Exception{

        Recipient savedRecipient = new Recipient();
        savedRecipient.setId(1l);

        when(recipientRepository.save(any(Recipient.class))).thenReturn(savedRecipient);
        when(recipientRepository.findByEmail(anyString())).thenReturn(null);

        ResponseEntity<CreateRecipientResponse> response =
                recipientService.createRecipient(new CreateRecipientRequest("John", "John@gmail.com"));

        assertThat(response.getStatusCode().toString()).isEqualTo("201 CREATED");
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(savedRecipient.getId());
        assertThat(response.getHeaders()).isNotNull();

        verify(recipientRepository, times(1)).save(any(Recipient.class));
    }
}
