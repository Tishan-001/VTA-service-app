package com.vta.app.components;

import com.vta.app.enums.GlobalConfigs;
import com.vta.app.repositories.ConfigsRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ContextConfigs {
    private final Map<String, String> configs = new HashMap<>();
    private final ConfigsRepository configsRepository;

    public ContextConfigs(ConfigsRepository configsRepository) {
        this.configsRepository = configsRepository;
    }

    @PostConstruct
    public void initiate() {
        configsRepository.findAll()
//                .doOnNext(config -> log.info("Loading config > {}", config.getName()))
                .map(config -> {
                    this.configs.put(config.getName(), config.getValue());
                    return config;
                }).blockLast();
    }

    public String getConfigAsString(String name, String defaultValue) {
        return configs.getOrDefault(name, defaultValue);
    }

    public String getConfig(GlobalConfigs globalConfigs) {
        return configs.getOrDefault(globalConfigs.getConfigName(), globalConfigs.getDefaultValue());
    }


    public int getConfigAsInt(String name, int defaultValue) {
        return Integer.parseInt(configs.getOrDefault(name, String.valueOf(defaultValue)));
    }

}
