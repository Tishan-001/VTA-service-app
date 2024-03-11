package com.vta.app.documents;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_verification_codes")
@Data
@NoArgsConstructor
public class UserVerificationCode {
    @Id
    private String id;
    @Indexed
    private String contactMedium;
    @Indexed
    private long expireTime;
    private boolean used;
    private String code;

    public UserVerificationCode(String id, String code, String contactMedium, long expireTime) {
        this.id = id;
        this.code = code;
        this.contactMedium = contactMedium;
        this.expireTime = expireTime;
    }

    public boolean isActive() {
        return !used && expireTime > System.currentTimeMillis();
    }
}
