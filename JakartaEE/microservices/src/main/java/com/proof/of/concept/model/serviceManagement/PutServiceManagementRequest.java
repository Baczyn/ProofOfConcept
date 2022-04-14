package com.proof.of.concept.model.serviceManagement;

public class PutServiceManagementRequest {

    private int id;
    private int addressId;
    private boolean active;
    private String userName;

    public PutServiceManagementRequest(int id, int addressId, boolean active, String userName) {
        this.id = id;
        this.addressId = addressId;
        this.active = active;
        this.userName = userName;
    }

    public PutServiceManagementRequest() {}

    public PutServiceManagementRequest(ServiceManagementResponse response) {
        this.id = response.getId();
        this.addressId = response.getAddressId();
        this.active = response.isActive();
        this.userName = response.getUserName();
    }

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


    @Override
    public String toString() {
        return "ServiceManagementResponse{" +
                "id=" + id +
                ", addressId=" + addressId +
                ", active=" + active +
                ", userName='" + userName + '\'' +
                '}';
    }
}