package com.proof.of.concept.model;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EventRequest {

    @NotNull(message = "Event must have a event id!")
    private Integer eventId;

    @NotNull(message = "Event must have a number of free tickets!")
    private Integer numberOfTickets;

    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startingDateOfBooking;
}
