package com.example.typingtestserver.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RankingRequestDto {
  private String name;
  private String email;
  private double wpm;
  private int error;
  private double time;
  private String tier;
  private int totalCharacters;
  private double accuracy;

}

