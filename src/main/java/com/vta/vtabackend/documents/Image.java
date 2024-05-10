package com.vta.vtabackend.documents;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "images")
public class Image {
    @Id
    private String id;
    private String imageUrl;
    private String uploaderEmail;
    private String imageId;

}
