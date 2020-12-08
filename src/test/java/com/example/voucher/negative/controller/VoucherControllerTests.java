package com.example.voucher.negative.controller;

import com.example.voucher.controller.VoucherController;
import com.example.voucher.exception.ControllerExceptionHandler;
import com.example.voucher.model.rest.CreateSpecialOfferRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class VoucherControllerTests {

    @InjectMocks
    private VoucherController voucherController;

    private MockMvc mvc;

    private JacksonTester<CreateSpecialOfferRequest> jsonCreateRecipientRequest;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(voucherController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void givenEmptyRequest_whenCreateRecipient_thenReturnBadRequest() throws Exception {

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/special-offer").contentType(MediaType.APPLICATION_JSON).content(
                        jsonCreateRecipientRequest.write(new CreateSpecialOfferRequest("", 0.00, "")).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void givenEmptyRequest_whenCreateVoucher_thenReturnBadRequest() throws Exception {

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/special-offer").contentType(MediaType.APPLICATION_JSON).content(
                        jsonCreateRecipientRequest.write(new CreateSpecialOfferRequest("", 0.00, "")).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
