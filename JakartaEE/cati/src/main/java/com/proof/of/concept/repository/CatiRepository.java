package com.proof.of.concept.repository;

import com.proof.of.concept.exceptions.AddressNotFoundException;
import com.proof.of.concept.model.Address;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class CatiRepository {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager entityManager;

    public int getAddressId(Address address) throws AddressNotFoundException {
        System.out.println(address);
        List<Address> addresses = entityManager.createNamedQuery("Address.getAddressByParams", Address.class)
                .setParameter("country", address.getCountry())
                .setParameter("city", address.getCity())
                .setParameter("postalcode", address.getPostalCode())
                .setParameter("street", address.getStreet())
                .setParameter("streetnumber", address.getStreetNumber())
                .setParameter("flatnumber", address.getFlatNumber())
                .getResultList();
        return addresses.stream().findFirst().orElseThrow(AddressNotFoundException::new).getId();
    }

    @Transactional
    public Address createAddress(Address address){
        entityManager.persist(address);
        return address;
    }

    @Transactional
    public Address deleteAddress(int addressId){
        Address address =  entityManager.find(Address.class,addressId);
        if(address != null){
            entityManager.remove(address);
        }
        return address;
    }

    @Transactional
    public Address modifyAddress(Address address){
         return entityManager.merge(address);
    }

    public List<Address> getAllAddress(){
        return  entityManager.createNamedQuery("Address.findAll",Address.class).getResultList();
    }
}