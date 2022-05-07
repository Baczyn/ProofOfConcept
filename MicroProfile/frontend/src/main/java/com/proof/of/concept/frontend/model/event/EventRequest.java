package com.proof.of.concept.frontend.model.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    private String title;
    private Location location;
    private String description;
    private String date;
}
