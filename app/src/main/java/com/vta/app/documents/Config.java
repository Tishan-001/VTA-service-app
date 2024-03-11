package com.vta.app.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "configs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config {
    @Id
    private String name;
    private String value;

    public <T> void setValue(T value) {
        if (value instanceof List list) {
            this.value = String.join(",", (List) value);
        } else {
            this.value = (String) value;
        }
    }
}
