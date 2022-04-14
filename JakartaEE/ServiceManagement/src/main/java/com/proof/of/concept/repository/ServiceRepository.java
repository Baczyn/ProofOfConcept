package com.proof.of.concept.repository;


import com.proof.of.concept.model.Service;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@RequestScoped
public class ServiceRepository {
    @PersistenceContext(name = "jpa-unit")
    private EntityManager entityManager;

    public Service getServiceByAddressId(Integer addressId){
        Optional<Service> service =  entityManager.createNamedQuery("Service.getServiceByAddressId",Service.class).setParameter("addressid", addressId).getResultList().stream().findFirst();
        return service.orElse(null);
    }

    public Service updateActive(Service service){
            return entityManager.merge(service);
    }
}