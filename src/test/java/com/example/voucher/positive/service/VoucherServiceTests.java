package com.example.voucher.positive.service;


import com.example.voucher.model.entity.Recipient;
import com.example.voucher.model.entity.SpecialOffer;
import com.example.voucher.model.entity.Voucher;
import com.example.voucher.model.rest.CreateSpecialOfferRequest;
import com.example.voucher.model.rest.CreateSpecialOfferResponse;
import com.example.voucher.model.rest.GetVoucherByEmailResponse;
import com.example.voucher.model.rest.UseVoucherResponse;
import com.example.voucher.respository.RecipientRepository;
import com.example.voucher.respository.SpecialOfferRepository;
import com.example.voucher.respository.VoucherRepository;
import com.example.voucher.service.impl.RecipientServiceImpl;
import com.example.voucher.service.impl.VoucherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class VoucherServiceTests {

    @Mock
    private VoucherRepository voucherRepository;

    @Mock
    private RecipientRepository recipientRepository;

    @Mock
    private SpecialOfferRepository specialOfferRepository;

    @InjectMocks
    private VoucherServiceImpl voucherService;

    @Mock
    private RecipientServiceImpl recipientService;

    @Mock
    HttpServletRequest request;

    @BeforeEach
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void givenValidInput_whenCreateSpecialOffer_thenReturnCreated(){


        Voucher savedVoucher = new Voucher();
        savedVoucher.setId(1l);
        when(voucherRepository.save(any(Voucher.class))).thenReturn(savedVoucher);

        Page<Recipient> recipientPage = new PageImpl<>(new ArrayList<>());
        when(recipientRepository.findAll(any(Pageable.class))).thenReturn(recipientPage);

        SpecialOffer savedSpecialOffer = new SpecialOffer();
        savedSpecialOffer.setId(1l);
        when(specialOfferRepository.save(any(SpecialOffer.class))).thenReturn(savedSpecialOffer);

        ResponseEntity<CreateSpecialOfferResponse> response = voucherService.createSpecialOffer(new CreateSpecialOfferRequest("11.11 Sale", 0.00, LocalDate.now().plusMonths(1).toString()));

        assertThat(response.getStatusCode().toString()).isEqualTo("201 CREATED");
        assertThat(response.getBody().getId()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(savedSpecialOffer.getId());
        assertThat(response.getHeaders()).isNotNull();

        verify(specialOfferRepository, times(1)).save(any(SpecialOffer.class));
    }


    @Test
    public void givenValidInput_whenUseVoucher_thenReturnOk(){

        SpecialOffer specialOffer = new SpecialOffer();
        specialOffer.setExpirationDate(LocalDate.now().plusMonths(1));

        Voucher savedVoucher = new Voucher();
        savedVoucher.setId(1l);
        savedVoucher.setRecipient(new Recipient("jason", "jason@gmail.com"));
        savedVoucher.setUsed(false);

        savedVoucher.setSpecialOffer(specialOffer);

        when(voucherRepository.findByVoucherCode(anyString())).thenReturn(savedVoucher);
        when(voucherRepository.save(any(Voucher.class))).thenReturn(savedVoucher);

        ResponseEntity<UseVoucherResponse> response = voucherService.useVoucher("voucher-111", "jason@gmail.com");

        assertThat(response.getStatusCode().toString()).isEqualTo("200 OK");
    }

    @Test
    public void givenValidInput_whenGetVoucherByEmail_thenReturnOk(){

        Voucher savedVoucher = new Voucher();
        savedVoucher.setId(1l);
        savedVoucher.setRecipient(new Recipient("jason", "jason@gmail.com"));
        savedVoucher.setUsed(false);

        when(voucherRepository.findByVoucherCode(anyString())).thenReturn(savedVoucher);
        when(voucherRepository.save(any(Voucher.class))).thenReturn(savedVoucher);
        when(recipientService.getRecipientByEmail(anyString())).thenReturn(new Recipient());

        Page<Voucher> voucherPage = new PageImpl<>(new ArrayList<>());
        when(voucherRepository.findByRecipientAndIsUsed(any(Recipient.class), anyBoolean(), any(Pageable.class))).thenReturn(voucherPage);

        ResponseEntity<GetVoucherByEmailResponse> response = voucherService.getVoucherByEmail( "jason@gmail.com", 0, 10);

        assertThat(response.getStatusCode().toString()).isEqualTo("200 OK");
    }

}
