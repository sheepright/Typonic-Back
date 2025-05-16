package com.example.typingtestserver.Dto.Chat;

import lombok.Data;

@Data
public class WordKeywordRequestDto {
    private String keyword;
    private int count;
}
