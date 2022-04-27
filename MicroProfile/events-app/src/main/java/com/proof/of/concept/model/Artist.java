package com.proof.of.concept.model;

import jakarta.json.bind.annotation.JsonbTransient;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.util.List;

@Data
@NoArgsConstructor
public class Artist {

    private String name;
    private String description;
    private List<String> members;

    @JsonbTransient
    public Document getArtistDoc(){
        Document doc = new Document();
        doc.put("name",name);
        doc.put("description",description);
        doc.put("members",members);
        return doc;
    }
}
