package com.example.typingtestserver.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailCheckDto {
  private String name;
  private double wpm;
}
