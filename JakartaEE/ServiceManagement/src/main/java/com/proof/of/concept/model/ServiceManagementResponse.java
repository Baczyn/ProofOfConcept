package com.proof.of.concept.model;

public class ServiceManagementResponse {

    private int id;
    private int addressId;
    private boolean active;
    private String userName;

    public ServiceManagementResponse(int id, int addressId, boolean active, String userName) {
        this.id = id;
        this.addressId = addressId;
        this.active = active;
        this.userName = userName;
    }

    public ServiceManagementResponse(Service service) {
        this.id = service.getId();
        this.addressId = service.getAddressId();
        this.active = service.isActive();
        this.userName = service.getUserName();
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