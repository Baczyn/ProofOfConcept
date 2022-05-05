package com.proof.of.concept.model;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventRequest {

    @NotBlank(message = "Event must have a event id!")
    private String eventId;

    @NotNull(message = "Event must have a number of free tickets!")
    private Integer numberOfTickets;
}
