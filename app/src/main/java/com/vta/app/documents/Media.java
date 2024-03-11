package com.vta.app.documents;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "medias")
@Getter
@Setter
public class Media {
    @Id
    private String id;
    private String url;
    private String hotelId;

    public Media(String id, String url, String hotelId) {
        this.id = id;
        this.url = url;
        this.hotelId = hotelId;
    }
}
