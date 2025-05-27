package com.example.typingtestserver.Controller;

import com.example.typingtestserver.Dto.Chat.*;
import com.example.typingtestserver.Service.Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "OpenAi API", description = "코드, 단어, 키워드 등의 타이핑 예제 표시해주는 API")
@RestController
@RequestMapping("/gpt")
@RequiredArgsConstructor
public class Controller {

    private final Service service;

    @Operation(
            summary = "코드 예제 생성",
            description = "사용자가 선택한 버튼의 언어와 길이(short, middle, long)에 따른 결과 값 도출"
    )
    @PostMapping("/code")
    public ResponseEntity<String> generateResponse(@RequestBody CodeRequestDto dto) {
        return ResponseEntity.ok(service.codeExample(dto));
    }

    @Operation(
            summary = "코드 단어 예제 생성",
            description = "사용자가 선택한 버튼의 언어와 갯수(10, 25, 50)에 따른 결과 값 도출"
    )
    @PostMapping("/words")
    public ResponseEntity<List<String>> generateWords(@RequestBody WordRequestDto dto) {
        return ResponseEntity.ok(service.codeWords(dto));
    }

    @Operation(
            summary = "키워드 예제 생성(단어)",
            description = "사용자가 입력한 키워드와 관련된 단어들을 선택한 갯수에 따라 결과 값 도출"
    )
    @PostMapping("/wordKeyword")
    public ResponseEntity<List<String>> generateKeyword(@RequestBody WordKeywordRequestDto dto) {
        return ResponseEntity.ok(service.wordKeyword(dto));
    }

    @Operation(
            summary = "키워드 예제 생성(문장)",
            description = "사용자가 입력한 키워드와 관련된 문장에 따라 결과 값 도출"
    )
    @PostMapping("/sentenceKeyword")
    public ResponseEntity<String> generateSentenceKeyword(@RequestBody SentenceKeywordRequestDto dto) {
        return ResponseEntity.ok(service.sentenceKeyword(dto));
    }

    @Operation(
            summary = "복.붙 양식 변경",
            description = "사용자가 입력한 양식(문장)을 TYPONIC 양식(문장)으로 변환시켜 전달"
    )
    @PostMapping("/copy")
    public ResponseEntity<String> convertText(@RequestBody CopyRequestDto dto) {
        return ResponseEntity.ok(service.copyData(dto));
    }
}
