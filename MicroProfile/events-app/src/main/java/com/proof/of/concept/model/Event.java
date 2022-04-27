package com.proof.of.concept.model;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.bson.codecs.jsr310.LocalDateTimeCodec;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class Event implements Serializable {

    @NotEmpty(message = "All events must have a title!")
    private String title;

    @NotNull(message = "All events must have a artist!")
    private Artist artist;

    @NotNull(message = "All events must have a location!")
    private Location location;

    @NotNull(message = "All events must have a date in format: yyyy-MM-dd'T'HH:mm:ss!")
    @JsonbDateFormat(value = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

//    private Collection<Picture> images = new HashSet<>();

    @JsonbTransient
    public Document getEventDoc(){
        Document doc = new Document();
        doc.put("title",title);
        doc.put("artist",artist.getArtistDoc());
        doc.put("location",location.getLocationDoc());
        doc.put("date",date.toString());
//        doc.put("images",images);
        return doc;
    }

}
