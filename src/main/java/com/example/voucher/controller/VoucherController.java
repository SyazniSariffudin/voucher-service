package com.example.voucher.controller;

import com.example.voucher.model.rest.CreateSpecialOfferRequest;
import com.example.voucher.model.rest.CreateSpecialOfferResponse;
import com.example.voucher.model.rest.GetVoucherByEmailResponse;
import com.example.voucher.model.rest.UseVoucherResponse;
import com.example.voucher.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@Tag(name = "Voucher", description = "Voucher service API(s)")
public class VoucherController {

    @Autowired
    VoucherService voucherService;

    @GetMapping(value = "/voucher/{email}")
    @Operation(summary = "Get valid vouchers by email id", description = "Get valid vouchers by email id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetVoucherByEmailResponse.class))),
            @ApiResponse(responseCode = "404", description = "Voucher not found") })
    private ResponseEntity<GetVoucherByEmailResponse> getVouchersByEmail(
            @Parameter(description="Get vouchers by email id. Cannot be empty.", required=true)
            @PathVariable
            @Email
                    String email,

            @Min(0)
            @RequestParam(value = "page", defaultValue = "0", required = false)
                    int page,

            @Max(500)
            @RequestParam(value = "pageSize", defaultValue = "10", required = false)
                    int pageSize) {
        return voucherService.getVoucherByEmail(email, page, pageSize);
    }

    @PostMapping(value = "/special-offer", consumes = { MediaType.APPLICATION_JSON_VALUE })
    @Operation(summary = "Create a new special offer and generate unique voucher code for each recipient")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Special offer created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreateSpecialOfferResponse.class)))})
    private ResponseEntity<CreateSpecialOfferResponse> createSpecialOffer(@Valid @RequestBody CreateSpecialOfferRequest createSpecialOfferRequest) {
        return voucherService.createSpecialOffer(createSpecialOfferRequest);
    }

    @PutMapping(value = "/voucher/{voucher-code}/email/{email}")
    @Operation(summary = "Use voucher that assigned to recipient by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully use a voucher",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UseVoucherResponse.class)))})
    private ResponseEntity<UseVoucherResponse> createVoucher(
            @Min(8)
            @Parameter(description="Voucher Code, obtained after creating special offer", required=true)
            @PathVariable(name = "voucher-code")
                    String voucherCode,

            @Parameter(description="Recipient's email", required=true)
            @PathVariable
                    String email) {
        return voucherService.useVoucher(voucherCode, email);
    }
}
