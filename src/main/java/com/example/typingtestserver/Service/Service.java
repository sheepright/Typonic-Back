package com.example.typingtestserver.Service;

import com.example.typingtestserver.Dto.Chat.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate;

    private String API_URL = "https://api.openai.com/v1/chat/completions";

    // ✅ ChatRequest 생성
    private ChatRequest createChatRequest(String prompt) {
        ChatRequest.Message message = new ChatRequest.Message();
        message.setRole("user");
        message.setContent(prompt);

        ChatRequest request = new ChatRequest();
        request.setModel(model);
        request.setMessages(List.of(message));
        return request;
    }

    // ✅ GPT API 호출 공통 메서드
    private ChatResponse sendRequest(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ChatRequest> requestEntity = new HttpEntity<>(createChatRequest(prompt), headers);

        ResponseEntity<ChatResponse> responseEntity = restTemplate.exchange(
                API_URL,
                HttpMethod.POST,
                requestEntity,
                ChatResponse.class
        );

        return responseEntity.getBody();
    }

    // ✅ 코드 예제 생성
    public String codeExample(CodeRequestDto dto) {
        String prompt = String.format(
                "%s 언어로 %s 길이의 코드 예제를 작성해줘. 코드만 표시해주고 설명은 생략해줘. " +
                        "여기서 short, middle, long 는 변수명이나 등이 아니라 길이를 말하는거야",
                dto.getLanguage(), dto.getLength());
        return extractTextContent(sendRequest(prompt));
    }

    // ✅ 키워드 기반 연관 문장 생성
    public String sentenceKeyword(SentenceKeywordRequestDto dto) {
        String prompt = String.format("%s와 연관된 적당한 길이의 문장 하나 작성해줘.", dto.getKeyword());
        return extractTextContent(sendRequest(prompt));
    }

    // ✅ 프로그래밍 단어 리스트 생성
    public List<String> codeWords(WordRequestDto dto) {
        String prompt = String.format(
                "%s 언어에서 자주 쓰이는 프로그래밍 단어를 %d개 콤마로 구분해서 출력해줘. 예: int, class, return...",
                dto.getLanguage(), dto.getCount());
        return extractWordList(sendRequest(prompt));
    }

    // ✅ 키워드 기반 연관 단어 리스트 생성
    public List<String> wordKeyword(WordKeywordRequestDto dto) {
        String prompt = String.format(
                "%s와 연관된 단어들을 %d개 콤마로 구분해서 출력해줘. 예: 노래 → 가사, 음표, 가수...",
                dto.getKeyword(), dto.getCount());
        return extractWordList(sendRequest(prompt));
    }

    // ✅ 응답 텍스트 추출 메서드 (단일 문자열용)
    private String extractTextContent(ChatResponse response) {
        return response.getChoices().stream()
                .findFirst()
                .map(choice -> choice.getMessage().getContent())
                .orElse("❌ 응답 없음");
    }

    // ✅ 응답 단어 리스트 추출 메서드
    private List<String> extractWordList(ChatResponse response) {
        String raw = extractTextContent(response);
        return Arrays.stream(raw.split("[,\\n]+"))
                .map(String::trim)
                .filter(word -> !word.isEmpty())
                .toList();
    }
}