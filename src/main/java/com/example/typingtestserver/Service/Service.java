package com.example.typingtestserver.Service;

import com.example.typingtestserver.Dto.Chat.*;
import com.example.typingtestserver.Entity.OpenAi;
import com.example.typingtestserver.Repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Transactional
public class Service {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate;
    private final ChatRepository chatRepository;
    private final Tika tika = new Tika();
    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    private String extractText(MultipartFile file) throws IOException, TikaException {
        return tika.parseToString(file.getInputStream());
    }

    private String API_URL = "https://api.openai.com/v1/chat/completions";

    // ✅ ChatRequest 생성
    private ChatRequest createChatRequest(String prompt) {
        ChatRequest.Message message = new ChatRequest.Message();
        message.setRole("user");
        message.setContent(prompt);

        ChatRequest request = new ChatRequest();
        request.setModel(model);
        request.setMessages(List.of(message));
        request.setTemperature(0.8);
        request.setN(1);
        return request;
    }

    // ✅ OpenAi API 호출 공통 메서드
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
                "%s 언어로 %s 길이의 코드 예제를 만들어줘. 코드는 완전한 형태로 제공하고, 주석은 포함하지 말아줘. " +
                        "항상 새로운 예제를 만들어줘서 같은 요청이라도 서로 다른 코드를 생성할 수 있도록 해줘. 설명은 생략하고 코드만 줘.",
                dto.getLanguage(), dto.getLength()
        );
        String codeExample = extractTextContent(sendRequest(prompt));

        OpenAi openAi = new OpenAi();
        openAi.setClassification(0);
        openAi.setLanguage(dto.getLanguage());
        openAi.setLength(dto.getLength());
        openAi.setCount(null);
        openAi.setResult(codeExample);
        chatRepository.save(openAi);

        return codeExample;
    }

    // ✅ 키워드 기반 연관 문장 생성
    public String sentenceKeyword(SentenceKeywordRequestDto dto) {
        String prompt = String.format(
                "\"%s\"와 관련된 짧고 명확한 문장을 한 문장만 만들어줘. 항상 새로운 표현을 사용해서 매번 다르게 생성해줘. 설명은 생략하고 문장만 줘.",
                dto.getKeyword()
        );
        return extractTextContent(sendRequest(prompt));
    }

    // ✅ 프로그래밍 단어 리스트 생성
    public List<String> codeWords(WordRequestDto dto) {
        String prompt = String.format(
                "%s 언어에서 많이 쓰이는 프로그래밍 키워드나 함수명을 %d개 알려줘. " +
                        "중복 없이 다양하게 골라주고, 콤마로 구분해서 출력해줘. 매 요청마다 가능한 다른 단어들을 선택해줘. 설명은 생략하고 문장만 줘.",
                dto.getLanguage(), dto.getCount()
        );

        List<String> words = extractWordList(sendRequest(prompt));

        String wordsString = String.join(", ", words);

        OpenAi openAi = new OpenAi();
        openAi.setClassification(1);
        openAi.setLanguage(dto.getLanguage());
        openAi.setLength(null);
        openAi.setCount(dto.getCount());
        openAi.setResult(wordsString);
        chatRepository.save(openAi);

        return words;
    }

    // ✅ 키워드 기반 연관 단어 리스트 생성
    public List<String> wordKeyword(WordKeywordRequestDto dto) {
        String prompt = String.format(
                "\"%s\"와 관련된 단어들을 %d개 골라서 콤마로 구분해줘. 일반적인 단어뿐만 아니라 연상되는 단어들도 포함해서 다양하게 구성해줘. 설명은 생략하고 문장만 줘.",
                dto.getKeyword(), dto.getCount()
        );
        return extractWordList(sendRequest(prompt));
    }

    // ✅ 사용자 문장을 사용하기 편리하게 변환
    public String copyData(CopyRequestDto dto) {
        String prompt = copyDataPrompt(dto.getCopyData());
        return extractTextContent(sendRequest(prompt));
    }

    // ✅ 파일 문자열로 추출
    public String fileExtractService(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }
        try {
            String extractedText = extractText(file);
            String prompt = copyDataPrompt(extractedText);

            return extractTextContent(sendRequest(prompt));

        } catch (IOException | TikaException e) {
            logger.error("파일 처리 중 오류 발생: {}", e.getMessage(), e);

            throw new RuntimeException("파일 처리 실패: " + e.getMessage());
        }
    }

    // ✅ 중복되는 프롬프트 설정
    private String copyDataPrompt(String sentence) {
        return String.format(
                """
                        다음 문장을 HTML의 아래 CSS 영역 안에 자연스럽게 출력될 수 있도록 예쁘게 다듬어줘.
                        다듬어진 결과는 문장만 출력하고, HTML 태그나 설명은 포함하지 마.
                        너무 길면 읽기 좋게 문장을 나누되 자연스럽게 이어지도록 해줘.
    
                        CSS:
                        <div className="w-[900px] h-auto bg-cdark rounded-br-[5px] rounded-bl-[5px] pb-[10px] shadow-lg">
    
                        문장:
                        %s
                        """,
                sentence
        );
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