package com.example.typingtestserver.Controller;

import com.example.typingtestserver.Dto.CodeRequestDto;
import com.example.typingtestserver.Dto.SentenceKeywordRequestDto;
import com.example.typingtestserver.Dto.WordKeywordRequestDto;
import com.example.typingtestserver.Dto.WordRequestDto;
import com.example.typingtestserver.Service.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Controller {
    private final Service service;

    @PostMapping("/code")
    public Mono<String> generateResponse(@RequestBody CodeRequestDto dto) {
        return service.codeExample(dto);
    }

    @PostMapping("/words")
    public Mono<List<String>> generateWords(@RequestBody WordRequestDto dto) {
        return service.codeWords(dto);
    }

    @PostMapping("/wordKeyword")
    public Mono<List<String>> generateKeyword(@RequestBody WordKeywordRequestDto dto) {
        return service.wordKeyword(dto);
    }

    @PostMapping("/sentenceKeyword")
    public Mono<String> generateSentenceKeyword(@RequestBody SentenceKeywordRequestDto dto) {
        return service.sentenceKeyword(dto);
    }
}
