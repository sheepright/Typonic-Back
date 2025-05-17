package com.example.typingtestserver.Dto.Ranking;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "랭킹 정보 DTO")
@Getter
@Setter
@ToString
public class RankingRequestDto {

    @Schema(description = "사용자 이름", example = "userName")
    private String name;
    @Schema(description = "사용자 이메일", example = "userName@gmail.com")
    private String email;
    @Schema(description = "wpm 기록 (실수)", example = "500")
    private double wpm;
    @Schema(description = "오타 갯수 (정수)", example = "5")
    private int error;
    @Schema(description = "걸린 시간 (실수)", example = "30")
    private double time;
    @Schema(description = "등급 (이동수단)", example = "스포츠카")
    private String tier;
    @Schema(description = "총 단어 갯수 (정수)", example = "50")
    private int totalCharacters;
    @Schema(description = "정확도 (실수)", example = "99.4")
    private double accuracy;

}

