package com.example.typingtestserver.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "GPT를 통해 받아온 데이터 정보 저장")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "GPTTable")
public class OpenAi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "자동 생성되는 고유 ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "문장, 단어 분류", example = "0 or 1")
    private Integer classification;
    @Schema(description = "프로그래밍 언어", example = "C++")
    private String language;
    @Schema(description = "문장의 길이", example = "short")
    private String length;
    @Schema(description = "단어의 갯수", example = "10")
    private Integer count;
    @Schema(description = "결과 값", example = "code or words")
    @Column(columnDefinition = "TEXT")
    private String result;
}
