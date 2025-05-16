package com.example.typingtestserver.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "RankingTable")
@Getter
@Setter
@NoArgsConstructor
public class Ranking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private double wpm;
    private int error;
    private double time;
    private String tier;
    private int totalCharacters;
    private double accuracy;

    //totalCharacters 컬럼 매핑시 자꾸 total_characters로 변환후 새로생성하길래 application.properties에 설정추가해놨음
}
