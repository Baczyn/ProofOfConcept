package com.proof.of.concept.frontend.model.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventResponse {

    private String id;
    private String title;
    private Location location;
    private String description;
    private LocalDateTime date;
}
