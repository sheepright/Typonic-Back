package com.example.typingtestserver.Dto.Chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "코드 예제 생성 DTO")
@Data
public class CodeRequestDto {

    @Schema(description = "사용할 프로그래밍 언어", example = "Java")
    private String language;
    @Schema(description = "코드 예제 길이 (short, middle, long)", example = "short")
    private String length;
}
