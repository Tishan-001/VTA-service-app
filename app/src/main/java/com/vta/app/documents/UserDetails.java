package com.vta.app.documents;

import com.vta.app.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@Document(collection = "users")
public class UserDetails {
    @Id
    private final String id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private Role role;
    private boolean verified;
}
