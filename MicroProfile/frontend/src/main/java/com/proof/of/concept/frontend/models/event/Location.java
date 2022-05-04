package com.proof.of.concept.frontend.models.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    private String country;
    private String city;
    private String streetName;
    private String streetNumber;
    private String facilityName;
}
