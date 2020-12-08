package com.example.voucher.negative.controller;

import com.example.voucher.controller.RecipientController;
import com.example.voucher.exception.ControllerExceptionHandler;
import com.example.voucher.model.rest.CreateRecipientRequest;
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
public class RecipientControllerTests {

    @InjectMocks
    private RecipientController recipientController;

    private MockMvc mvc;

    private JacksonTester<CreateRecipientRequest> jsonUtil;

    @BeforeEach
    public void setup() {

        JacksonTester.initFields(this, new ObjectMapper());

        mvc = MockMvcBuilders.standaloneSetup(recipientController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void givenExistingEmail_whenCreateRecipient_thenReturnBadRequest() throws Exception {

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/recipient").contentType(MediaType.APPLICATION_JSON).content(
                        jsonUtil.write(new CreateRecipientRequest("John", "John")).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void givenEmptyRequest_whenCreateRecipient_thenReturnBadRequest() throws Exception {

        // when
        MockHttpServletResponse response = mvc.perform(
                post("/recipient").contentType(MediaType.APPLICATION_JSON).content(
                        jsonUtil.write(new CreateRecipientRequest("", "")).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


}
