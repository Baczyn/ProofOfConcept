package com.proof.of.concept.model;

import java.util.UUID;

public class IdGenerator {

    public static Integer generateUniqueId() {
        int uuid = UUID.randomUUID().toString().hashCode();
        String filteredUuid = String.valueOf(uuid).replaceAll("-", "");
        return Integer.parseInt(filteredUuid);
    }
}
