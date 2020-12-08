package com.example.voucher.service.impl;

import com.example.voucher.exception.BusinessValidationException;
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
import com.example.voucher.service.RecipientService;
import com.example.voucher.service.VoucherService;
import com.example.voucher.util.CleanShutdownExecutorService;
import com.example.voucher.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;

@Component
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    SpecialOfferRepository specialOfferRepository;

    @Autowired
    RecipientRepository recipientRepository;

    @Autowired
    RecipientService recipientService;

    private CleanShutdownExecutorService cleanShutdownExecutorService = new CleanShutdownExecutorService(Executors.newFixedThreadPool(10));

    @Override
    public ResponseEntity<CreateSpecialOfferResponse> createSpecialOffer(CreateSpecialOfferRequest createSpecialOfferRequest) {

        validateBeforeCreateSpecialOffer(createSpecialOfferRequest);

        SpecialOffer specialOffer = new SpecialOffer(createSpecialOfferRequest);
        SpecialOffer savedSpecialOffer = specialOfferRepository.save(specialOffer);

        generateVoucherForRecipients(savedSpecialOffer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedSpecialOffer.getId()).toUri();

        return ResponseEntity.created(location).body(new CreateSpecialOfferResponse(savedSpecialOffer));
    }

    private void validateBeforeCreateSpecialOffer(CreateSpecialOfferRequest createSpecialOfferRequest){
        LocalDate expirationDate;
        try {
            expirationDate = DateUtil.convert(createSpecialOfferRequest.getExpirationDate());
        } catch (DateTimeParseException ex) {
            throw new BusinessValidationException("Date format must be in yyyy-MM-dd");
        }

        boolean dateIsPassed = DateUtil.dateIsPassed(expirationDate);

        if(!dateIsPassed){
            throw new BusinessValidationException("Date should be more than today date and format must be in yyyy-MM-dd");
        }
    }

    private void generateVoucherForRecipients(SpecialOffer savedSpecialOffer){

        cleanShutdownExecutorService.execute(() -> {
            Pageable pageRequest = PageRequest.of(0, 200);
            Page<Recipient> recipientPage = recipientRepository.findAll(pageRequest);

            while (!recipientPage.isEmpty()) {
                saveVoucherForRecipients(recipientPage.getContent(), savedSpecialOffer);

                pageRequest = pageRequest.next();
                recipientPage = recipientRepository.findAll(pageRequest);
            }
        });
    }

    private void saveVoucherForRecipients(List<Recipient> recipients, SpecialOffer savedSpecialOffer){

        recipients.forEach(recipient -> {
            Voucher voucher = new Voucher();
            voucher.setVoucherCode(UUID.randomUUID().toString());
            voucher.setRecipient(recipient);
            voucher.setSpecialOffer(savedSpecialOffer);

            voucherRepository.save(voucher);
        });

    }

    @Override
    public ResponseEntity<UseVoucherResponse> useVoucher(String voucherCode, String email) {

        Voucher savedVoucher = voucherRepository.findByVoucherCode(voucherCode);

        validateBeforeUseVoucher(savedVoucher, email);

        savedVoucher.setUsed(true);
        savedVoucher.setUsageDate(LocalDate.now());

        Voucher updatedVoucher = voucherRepository.save(savedVoucher);

        return ResponseEntity.ok(new UseVoucherResponse(updatedVoucher));
    }

    public void validateBeforeUseVoucher(Voucher voucher, String email) {
        if(voucher==null){
            throw new BusinessValidationException("Invalid voucher");
        }

        if(!voucher.getRecipient().getEmail().equalsIgnoreCase(email)){
            throw new BusinessValidationException("Email is not valid with voucher");
        }

        if(voucher.isUsed()){
            throw new BusinessValidationException("Voucher has been used at "+voucher.getUsageDate());
        }

        if(!DateUtil.dateIsPassed(voucher.getSpecialOffer().getExpirationDate())){
            throw new BusinessValidationException("Voucher has been expired at "+voucher.getSpecialOffer().getExpirationDate());
        }
    }

    @Override
    public ResponseEntity<GetVoucherByEmailResponse> getVoucherByEmail(String email, int page, int pageSize) {

        Recipient recipient = recipientService.getRecipientByEmail(email);

        if(recipient==null){
            throw new BusinessValidationException("Email doesn't associate with any user");
        }

        Page<Voucher> voucherPage = voucherRepository.findByRecipientAndIsUsed(recipient, false, PageRequest.of(page, pageSize));
        List<Voucher> vouchers = voucherPage.getContent();

        GetVoucherByEmailResponse getVoucherByEmailResponse = new GetVoucherByEmailResponse();
        getVoucherByEmailResponse.setPageNumber(voucherPage.getNumber());
        getVoucherByEmailResponse.setPageSize(voucherPage.getSize());
        getVoucherByEmailResponse.setRecordsFiltered(voucherPage.getNumberOfElements());
        getVoucherByEmailResponse.setRecordsTotal((int) voucherPage.getTotalElements());
        getVoucherByEmailResponse.setData(vouchers);

        return ResponseEntity.ok(getVoucherByEmailResponse);
    }
}
