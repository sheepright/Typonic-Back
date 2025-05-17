package com.example.typingtestserver.Dto.Chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "코드 단어 예제 생성 DTO")
@Data
public class WordRequestDto {
    @Schema(description = "프로그래밍 언어", example = "C++")
    private String language;
    @Schema(description = "단어 갯수 (10, 25, 50)", example = "10")
    private int count;
}
