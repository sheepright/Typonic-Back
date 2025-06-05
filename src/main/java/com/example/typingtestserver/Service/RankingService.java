package com.example.typingtestserver.Service;

import com.example.typingtestserver.Dto.Ranking.RankingRequestDto;
import com.example.typingtestserver.Entity.Ranking;
import com.example.typingtestserver.Repository.RankingRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RankingService {

    @Autowired
    private RankingRepository repository;

    // 비속어 리스트
    private List<String> bannedWords = new ArrayList<>();

    // 서비스 시작 시 비속어 JSON 파일을 로딩
    @PostConstruct
    public void loadBannedWords() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("badwords.json");
            bannedWords = mapper.readValue(resource.getInputStream(), new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("비속어 리스트를 불러오는 데 실패했습니다.", e);
        }
    }

    // 이름에 비속어 포함 여부 확인
    private boolean containsProfanity(String input) {
        if (input == null) return false;
        String lowerInput = input.toLowerCase();
        return bannedWords.stream().anyMatch(lowerInput::contains);
    }

    // 랭킹 저장
    public String saveRanking(RankingRequestDto dto) {
        if (containsProfanity(dto.getName())) {
            throw new IllegalArgumentException("비속어 등의 부적절한 단어를 포함할 수 없습니다.");
        }

        Optional<Ranking> existing = repository.findByEmail(dto.getEmail());
        Ranking ranking;
        String message;

        if (existing.isPresent()) {
            ranking = existing.get();
            message = "업데이트 완료";
        } else {
            ranking = new Ranking();
            ranking.setEmail(dto.getEmail());
            message = "새로운 랭킹 저장";
        }

        ranking.setName(dto.getName());
        ranking.setWpm(dto.getWpm());
        ranking.setError(dto.getError());
        ranking.setTime(dto.getTime());
        ranking.setTier(dto.getTier());
        ranking.setTotalCharacters(dto.getTotalCharacters());
        ranking.setAccuracy(dto.getAccuracy());
        ranking.setDate(LocalDateTime.now());

        repository.save(ranking);
        return message;
    }

    // WPM 백분위
    public double getPercentile(double wpm) {
        long total = repository.count();
        long above = repository.countByWpmGreaterThan(wpm);
        return ((double) above / total) * 100;
    }

    // 상위 50개 랭킹
    public List<Ranking> getTop50() {
        return repository.findTop50ByOrderByWpmDescAccuracyDesc();
    }

    // 이메일로 랭킹 조회
    public boolean checkEmailExists(String email) {
        return repository.findByEmail(email).isPresent();
    }
}