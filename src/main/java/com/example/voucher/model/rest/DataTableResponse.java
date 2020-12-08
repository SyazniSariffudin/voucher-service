package com.example.voucher.model.rest;

public class DataTableResponse {
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private Integer pageNumber;
    private Integer pageSize;

    public DataTableResponse() {
    }

    public DataTableResponse(Integer recordsTotal, Integer recordsFiltered) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public DataTableResponse(Integer recordsTotal, Integer recordsFiltered, Integer pageNumber, Integer pageSize) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "{" +
                "recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}
