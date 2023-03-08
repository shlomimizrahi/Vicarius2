package com.example.vicarius2;

import java.util.Map;

public record CreateIndexRequestDTO(Map<String, Object> settings, Map<String, Object> mappings) {}
