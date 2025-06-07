package com.example.typingtestserver.Dto.Ranking;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RankingResponseDto {
    private String name;
    private double wpm;
    private double accuracy;
    private double time;
    private LocalDateTime date;
}
