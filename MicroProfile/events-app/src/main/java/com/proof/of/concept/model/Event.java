package com.proof.of.concept.model;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Event implements Serializable {

    @NotEmpty(message = "All events must have a title!")
    private String title;

    @NotNull(message = "All events must have a location!")
    private Location location;

    private String description;

    @NotNull(message = "All events must have a date in format: yyyy-MM-dd'T'HH:mm:ss!")
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

//    private Collection<Picture> images = new HashSet<>();

    @JsonbTransient
    public Document getEventDoc() {
        Document doc = new Document();
        doc.put("title", title);
        doc.put("location", location.getLocationDoc());
        doc.put("description", description);
        doc.put("date", date.toString());
//        doc.put("images",images);
        return doc;
    }
}
