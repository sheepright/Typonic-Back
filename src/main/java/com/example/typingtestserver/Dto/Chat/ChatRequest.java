package com.example.typingtestserver.Dto.Chat;

import lombok.Data;

import java.util.List;

@Data
public class ChatRequest {
    private String model;
    private List<Message> messages;

    // ✅ 다양성 제어(0.0 ~ 2.0)
    private Double temperature;

    // ✅ 응답 개수: 생성할 답변의 수
    private Integer n;

    @Data
    public static class Message {
        private String role;
        private String content;
    }
}
