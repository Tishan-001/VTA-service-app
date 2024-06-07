package com.vta.vtabackend.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        final Map<String, Object> config = new HashMap<>();
        config.put("cloud_name", "dyie6dtcm");
        config.put("api_key", "547513523316435");
        config.put("api_secret", "TIt71jwwoUULbiak_OSB4C-n8Hw");
        return new Cloudinary(config);
    }
}
