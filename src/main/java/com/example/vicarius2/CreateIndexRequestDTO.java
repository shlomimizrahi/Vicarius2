package com.example.vicarius2;

import java.util.Map;

public class CreateIndexRequestDTO {
    private Map<String, Object> settings;
    private Map<String, Object> mappings;

    public Map<String, Object> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, Object> settings) {
        this.settings = settings;
    }

    public Map<String, Object> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, Object> mappings) {
        this.mappings = mappings;
    }
}
