package com.example.typingtestserver.Dto.Chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "키워드 (단어) 생성 DTO")
@Data
public class WordKeywordRequestDto {
    @Schema(description = "키워드 (단어)", example = "명언, 과일 등등")
    private String keyword;
    @Schema(description = "단어 갯수 (10, 25, 30)", example = "10")
    private int count;
}
