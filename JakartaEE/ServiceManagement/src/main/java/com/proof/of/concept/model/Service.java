package com.proof.of.concept.model;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table(name = "Service")
@NamedQuery(name = "Service.getServiceByAddressId", query = "SELECT s FROM Service s where s.addressId=:addressid")
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private int id;
    private int addressId;
    private boolean active;
    private String userName;

    public Service(int id, int addressId, boolean active, String userName) {
        this.id = id;
        this.addressId = addressId;
        this.active = active;
        this.userName = userName;
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

    public Service() {
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", addressId=" + addressId +
                ", active=" + active +
                ", userName='" + userName + '\'' +
                '}';
    }
}