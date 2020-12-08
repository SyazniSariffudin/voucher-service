package com.example.voucher.model.exception;

import java.util.HashMap;
import java.util.Map;

public class ValidationResults {

    Map<String, String> results = new HashMap<>();

    public void addResult(String field, String errorMessage){
        results.put(field, errorMessage);
    }

    public  Map<String, String> getResults(){
        return results;
    }
}
