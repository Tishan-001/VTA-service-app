package com.vta.vtabackend.documents;

import com.vta.vtabackend.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserDetails {
    @Id
    private String id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private Role role;
    private boolean verified;
}
