package com.example.typingtestserver.Dto.Chat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "복.붙 데이터 변환 DTO")
@Data
public class CopyRequestDto {
    private String copyData;
}
