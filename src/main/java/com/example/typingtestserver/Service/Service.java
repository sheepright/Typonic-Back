package com.example.typingtestserver.Service;

import com.example.typingtestserver.Dto.Chat.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private final WebClient.Builder webClientBuilder;

    // ✅ WebClient 재사용
    private WebClient getWebClient() {
        return webClientBuilder
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

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
    private Mono<ChatResponse> sendChatRequest(String prompt) {
        return getWebClient()
                .post()
                .bodyValue(createChatRequest(prompt))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        (ClientResponse response) -> response.bodyToMono(String.class).flatMap(errorBody -> {
                            System.err.println("❌ GPT 호출 실패: " + errorBody);
                            return Mono.error(new RuntimeException("OpenAI API 오류: " + errorBody));
                        })
                )
                .bodyToMono(ChatResponse.class);
    }

    // ✅ 코드 예제 생성
    public Mono<String> codeExample(CodeRequestDto dto) {
        String prompt = String.format(
                "%s 언어로 %s 길이의 코드 예제를 작성해줘. 코드만 표시해주고 설명은 생략해줘. " +
                        "여기서 short, middle, long 는 변수명이나 등이 아니라 길이를 말하는거야",
                dto.getLanguage(), dto.getLength());

        return sendChatRequest(prompt)
                .map(this::extractTextContent);
    }
    // ✅ 키워드 기반 연관 문장 생성
    public Mono<String> sentenceKeyword(SentenceKeywordRequestDto dto) {
        String prompt = String.format(
                "%s와 연관된 적당한 길이의 문장 하나 작성해줘.", dto.getKeyword());
        return sendChatRequest(prompt)
                .map(this::extractTextContent);
    }

    // ✅ 프로그래밍 단어 리스트 생성
    public Mono<List<String>> codeWords(WordRequestDto dto) {
        String prompt = String.format(
                "%s 언어에서 자주 쓰이는 프로그래밍 단어를 %d개 콤마로 구분해서 출력해줘. 예: int, class, return...",
                dto.getLanguage(), dto.getCount());

        return sendChatRequest(prompt)
                .map(this::extractWordList);
    }

    // ✅ 키워드 기반 연관 단어 리스트 생성
    public Mono<List<String>> wordKeyword(WordKeywordRequestDto dto) {
        String prompt = String.format(
                "%s와 연관된 단어들을 %d개 콤마로 구분해서 출력해줘. 예: 노래 → 가사, 음표, 가수...",
                dto.getKeyword(), dto.getCount());

        return sendChatRequest(prompt)
                .map(this::extractWordList);
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
                .collect(Collectors.toList());
    }
}