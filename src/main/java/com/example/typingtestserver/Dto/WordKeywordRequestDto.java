package com.example.typingtestserver.Dto;

import lombok.Data;

@Data
public class WordKeywordRequestDto {
    private String keyword;
    private int count;
}
