package com.vta.app.models;

import com.vta.app.enums.Gender;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@Builder
public class Personal {
    private String fullName;
    private Gender gender;
    private String nationality;
}
