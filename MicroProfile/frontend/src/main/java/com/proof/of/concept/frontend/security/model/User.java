package com.proof.of.concept.frontend.security.model;

import jakarta.json.JsonObject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String name;
    private String password;

    private Set<Role> roles;

    static public User fromJson(String userJson) {
        return JsonbBuilder.create(new JsonbConfig().withAdapters(new UserAdapter())).fromJson(userJson, User.class);
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    @JsonbTransient
    public Document getUserDoc() {
        Document doc = new Document();
        doc.put("name", name);
        doc.put("password", password);
        doc.put("roles", roles.stream().map(Enum::name).collect(Collectors.joining()));
        return doc;
    }

    public static class UserBuilder {

        private String name;
        private String password;
        private Set<Role> roles;

        private Pbkdf2PasswordHash passwordHash;

        private UserBuilder() {
        }

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder withRoles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public UserBuilder withPasswordHash(Pbkdf2PasswordHash passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public User build() {
            requireNonNull(name, "name is required");
            requireNonNull(password, "password is required");
            requireNonNull(roles, "roles is required");
            requireNonNull(passwordHash, "passwordHash is required");

            User user = new User();
            user.roles = roles;
            user.name = name;
            user.password = passwordHash.generate(password.toCharArray());
            return user;
        }
    }

    public static class UserAdapter implements JsonbAdapter<User, JsonObject> {

        @Override
        public JsonObject adaptToJson(User user) throws Exception {
            return null;
        }

        @Override
        public User adaptFromJson(JsonObject adapted) throws Exception {
            User user = new User();
            Set<Role> roles = new HashSet<>();
            user.setName(adapted.getString("name"));
            user.setPassword(adapted.getString("password"));
            roles.add(Role.valueOf(adapted.getString("roles")));
            user.setRoles(roles);
            return user;
        }
    }
}
