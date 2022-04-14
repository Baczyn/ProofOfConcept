package com.proof.of.concept.model.serviceManagement;

public class ServiceManagementResponse {

    private int id;
    private int addressId;
    private boolean active;
    private String userName;

    private String errorCode;
    private String errorDesc;

    public ServiceManagementResponse(int id, int addressId, boolean active, String userName) {
        this.id = id;
        this.addressId = addressId;
        this.active = active;
        this.userName = userName;
    }

    public ServiceManagementResponse(int id, int addressId, boolean active, String userName, String errorCode, String errorDesc) {
        this.id = id;
        this.addressId = addressId;
        this.active = active;
        this.userName = userName;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
    }

    public ServiceManagementResponse() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return "ServiceManagementResponse{" +
                "id=" + id +
                ", addressId=" + addressId +
                ", active=" + active +
                ", userName='" + userName + '\'' +
                ", errorCode='" + errorCode + '\'' +
                ", errorDesc='" + errorDesc + '\'' +
                '}';
    }
}