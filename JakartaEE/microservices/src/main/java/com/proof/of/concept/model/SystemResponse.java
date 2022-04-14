package com.proof.of.concept.model;

import jakarta.json.bind.annotation.JsonbTransient;

public class SystemResponse {

    private SystemRequest systemRequest;

    @JsonbTransient
    private int addressId;

    private String errorCode;
    private String errorDesc;

    public SystemResponse(SystemRequest systemRequest) {
        this.systemRequest = systemRequest;
    }

    public SystemResponse(SystemRequest systemRequest, String errorCode, String errorDesc) {
        this.systemRequest = systemRequest;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public SystemResponse(SystemRequest systemRequest, int addressId, String errorCode, String errorDesc) {
        this.systemRequest = systemRequest;
        this.addressId = addressId;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public SystemResponse() {
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public SystemRequest getSystemRequest() {
        return systemRequest;
    }

    public void setSystemRequest(SystemRequest systemRequest) {
        this.systemRequest = systemRequest;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    @Override
    public String toString() {
        return "SystemResponse{" +
                "systemRequest=" + systemRequest +
                ", errorCode='" + errorCode + '\'' +
                ", errorDesc='" + errorDesc + '\'' +
                '}';
    }
}