package com.example.ai_lol_assistant.model;

public class MatchDto {
    // 필드 정의
    private MetadataDto metadata;  // Match metadata
    private InfoDto info;          // Match information

    // Getters
    public MetadataDto getMetadata() {
        return metadata;
    }

    public InfoDto getInfo() {
        return info;
    }
}