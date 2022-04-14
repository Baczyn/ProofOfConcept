package com.proof.of.concept.model.cati;

public class CatiResponse {

    private int id;
    private String errorCode;
    private String errorDesc;

    public CatiResponse() {
    }

    public CatiResponse(int id, String errorCode, String errorDesc) {
        this.id = id;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}