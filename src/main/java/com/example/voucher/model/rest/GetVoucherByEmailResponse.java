package com.example.voucher.model.rest;

import com.example.voucher.model.entity.Voucher;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetVoucherByEmailResponse extends DataTableResponse{
    private List<Voucher> data;

    public GetVoucherByEmailResponse() {
    }

    public GetVoucherByEmailResponse(Integer recordsTotal, Integer recordsFiltered, List<Voucher> data) {
        super(recordsTotal, recordsFiltered);
        this.data = data;
    }

    public List<Voucher> getData() {
        return this.data;
    }

    public void setData(List<Voucher> data) {
        this.data = data;
    }

}
