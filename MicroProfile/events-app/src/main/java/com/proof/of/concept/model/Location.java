package com.proof.of.concept.model;

import jakarta.json.bind.annotation.JsonbTransient;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Data
@NoArgsConstructor
public class Location {

    private String country;
    private String city;
    private String streetName;
    private String streetNumber;
    private String facilityName;

    @JsonbTransient
    public Document getLocationDoc() {
        Document doc = new Document();
        doc.put("country", country);
        doc.put("city", city);
        doc.put("streetName", streetName);
        doc.put("streetNumber", streetNumber);
        doc.put("facilityName", facilityName);
        return doc;
    }
}
