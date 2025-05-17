package com.example.typingtestserver.Dto.Chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "키워드 (문장) 생성 DTO")
@Data
public class SentenceKeywordRequestDto {
    @Schema(description = "키워드 (문장)", example = "명언, 노래 등등")
    private String keyword;
}
