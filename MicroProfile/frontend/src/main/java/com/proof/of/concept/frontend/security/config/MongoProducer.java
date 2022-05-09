package com.proof.of.concept.frontend.security.config;


import com.ibm.websphere.ssl.SSLException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class MongoProducer {

    @Inject
    @ConfigProperty(name = "mongo.hostname", defaultValue = "localhost")
    String hostname;

    @Inject
    @ConfigProperty(name = "mongo.port", defaultValue = "27018")
    int port;

    @Inject
    @ConfigProperty(name = "mongo.dbname", defaultValue = "users")
    String dbName;

    @Inject
    @ConfigProperty(name = "mongo.user", defaultValue = "admin")
    String user;

//    @Inject
//    @ConfigProperty(name = "mongo.pass.encoded")
//    String encodedPass;

    @Inject
    @ConfigProperty(name = "mongo.pass", defaultValue = "password")
    String password;

    @Produces
    public MongoClient createMongo() throws SSLException {
//        String password = PasswordUtil.passwordDecode(password);
        MongoCredential creds = MongoCredential.createCredential(
                user,
                dbName,
                password.toCharArray()
        );
//        SSLContext sslContext = JSSEHelper.getInstance().getSSLContext(
//                "outboundSSLContext",
//                Collections.emptyMap(),
//                null
//        );

        return new MongoClient(
                new ServerAddress(hostname, port),
                creds,
                new MongoClientOptions.Builder()
//                        .sslEnabled(true)
//                        .sslContext(sslContext)
                        .build()
        );
    }

    @Produces
    public MongoDatabase createDB(MongoClient client) {
        return client.getDatabase(dbName);
    }

    public void close(@Disposes MongoClient toClose) {
        toClose.close();
    }
}
