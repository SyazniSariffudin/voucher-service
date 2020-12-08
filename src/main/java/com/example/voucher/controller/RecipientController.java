package com.example.voucher.controller;

import com.example.voucher.model.rest.CreateRecipientRequest;
import com.example.voucher.model.rest.CreateRecipientResponse;
import com.example.voucher.service.RecipientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Tag(name = "Recipient", description = "Recipient service API(s)")
public class RecipientController {

    @Autowired
    RecipientService recipientService;

    @PostMapping(value = "/recipient", consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Create a new recipient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipient created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreateRecipientResponse.class)))})
    private ResponseEntity<CreateRecipientResponse> createRecipient(@Valid @RequestBody CreateRecipientRequest createRecipientRequest) {
        return recipientService.createRecipient(createRecipientRequest);
    }
}
