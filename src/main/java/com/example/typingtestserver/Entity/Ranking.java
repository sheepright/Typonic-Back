package com.example.typingtestserver.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "랭킹 정보 DTO")
@Entity
@Table(name = "RankingTable")
@Getter
@Setter
@NoArgsConstructor
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "자동 생성되는 고유 ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

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
    //totalCharacters 컬럼 매핑시 자꾸 total_characters로 변환후 새로생성하길래 application.properties에 설정추가해놨음
    @Schema(description = "총 단어 갯수 (정수)", example = "50")
    private int totalCharacters;
    @Schema(description = "정확도 (실수)", example = "99.4")
    private double accuracy;
    @Schema(description = "DB 등록 시간", example = "2025-05-27T14:30:00")
    private LocalDateTime date;

}
