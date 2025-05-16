package com.example.typingtestserver.Dto.Chat;

import lombok.Data;

@Data
public class WordRequestDto {
    private String language;
    private int count;
}
