package com.example.voucher.negative.service;


import com.example.voucher.exception.BusinessValidationException;
import com.example.voucher.model.entity.Recipient;
import com.example.voucher.model.entity.SpecialOffer;
import com.example.voucher.model.entity.Voucher;
import com.example.voucher.model.rest.CreateSpecialOfferRequest;
import com.example.voucher.respository.SpecialOfferRepository;
import com.example.voucher.respository.VoucherRepository;
import com.example.voucher.service.impl.RecipientServiceImpl;
import com.example.voucher.service.impl.VoucherServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTests {

    @Mock
    private VoucherRepository voucherRepository;

    @Mock
    private SpecialOfferRepository specialOfferRepository;

    @InjectMocks
    private VoucherServiceImpl voucherService;

    @Mock
    private RecipientServiceImpl recipientService;

    @Test
    public void givenInvalidDate_whenCreateSpecialOffer_thenThrowBusinessValidationException(){

        try{
            voucherService.createSpecialOffer(new CreateSpecialOfferRequest("11.11 Sale", 0.00, "2020/03/19"));
        }catch (BusinessValidationException ex){
            assertThat(ex.getMessage()).isNotNull();
        }

        verify(specialOfferRepository, times(0)).save(any(SpecialOffer.class));
    }

    @Test
    public void givenYesterdayDate_whenCreateSpecialOffer_thenThrowBusinessValidationException(){

        try{
            voucherService.createSpecialOffer(new CreateSpecialOfferRequest("11.11 Sale", 0.00, LocalDate.now().minusMonths(1).toString()));
        }catch (BusinessValidationException ex){
            assertThat(ex.getMessage()).isNotNull();
        }

        verify(specialOfferRepository, times(0)).save(any(SpecialOffer.class));
    }

    @Test
    public void givenInvalidVoucher_whenUseVoucher_thenThrowBusinessValidationException(){

        when(voucherRepository.findByVoucherCode(anyString())).thenReturn(null);

        try{
            voucherService.useVoucher("voucher-111", "jason@gmail.com");
        }catch (BusinessValidationException ex){
            assertThat(ex.getMessage()).isNotNull();
        }

        verify(voucherRepository, times(0)).save(any(Voucher.class));
    }

    @Test
    public void givenInvalidEmail_whenUseVoucher_thenThrowBusinessValidationException(){

        Voucher voucher = new Voucher();
        voucher.setRecipient(new Recipient("Micheal", "Micheal@yahoo.com"));

        when(voucherRepository.findByVoucherCode(anyString())).thenReturn(voucher);

        try{
            voucherService.useVoucher("867bbe74-907d-4909-8662-3100d75859be", "jason@gmail.com");
        }catch (BusinessValidationException ex){
            assertThat(ex.getMessage()).isNotNull();
        }

        verify(voucherRepository, times(0)).save(any(Voucher.class));
    }

    @Test
    public void givenExpiredVoucher_whenUseVoucher_thenThrowBusinessValidationException(){

        Voucher voucher = new Voucher();
        voucher.setRecipient(new Recipient("Micheal", "Micheal@yahoo.com"));
        voucher.setUsed(true);

        when(voucherRepository.findByVoucherCode(anyString())).thenReturn(voucher);

        try{
            voucherService.useVoucher("867bbe74-907d-4909-8662-3100d75859be", "Micheal@yahoo.com");
        }catch (BusinessValidationException ex){
            assertThat(ex.getMessage()).isNotNull();
        }

        verify(voucherRepository, times(0)).save(any(Voucher.class));
    }

    @Test
    public void givenInvalidEmail_whenGetVoucherByEmail_thenThrowBusinessValidationException(){

        Voucher voucher = new Voucher();
        voucher.setRecipient(new Recipient("Micheal", "Micheal@yahoo.com"));
        voucher.setUsed(true);

        when(recipientService.getRecipientByEmail(anyString())).thenReturn(null);

        try{
            voucherService.getVoucherByEmail("Micheal@yahoo.com", 0, 10);
        }catch (BusinessValidationException ex){
            assertThat(ex.getMessage()).isNotNull();
        }

        verify(voucherRepository, times(0)).findByRecipientAndIsUsed(any(Recipient.class), anyBoolean(), any(Pageable.class));
        verify(voucherRepository, times(0)).save(any(Voucher.class));
    }
}
