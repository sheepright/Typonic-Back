package com.example.typingtestserver.Dto.Ranking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "이메일 중복 체크 DTO")
@Getter
@Setter
@AllArgsConstructor
public class EmailCheckDto {

    @Schema(description = "사용자 이름", example = "userName")
    private String name;
    @Schema(description = "wpm 기록", example = "500")
    private double wpm;
}
