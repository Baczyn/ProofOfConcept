package com.proof.of.concept.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;


@ApplicationScoped
public class NeoProducer {

    public static final String[] PACKAGES = {
            "com.proof.of.concept.model"
    };

    @Inject
    @ConfigProperty(name = "neo4j.databaseUri", defaultValue = "neo4j://localhost:7687")
    String databaseUri;

    @Inject
    @ConfigProperty(name = "neo4j.user", defaultValue = "neo4j")
    String user;

    @Inject
    @ConfigProperty(name = "neo4j.pass", defaultValue = "password")
    String password;

    @Produces
    SessionFactory produceSessionFactory() {
        Configuration neoConfig = new Configuration.Builder()
                .uri(databaseUri)
                .credentials(user, password)
                .useNativeTypes()
                .build();

        return new SessionFactory(neoConfig, PACKAGES);
    }

    void disposeSessionFactory(@Disposes SessionFactory sessionFactory) {
        sessionFactory.close();
    }
}
