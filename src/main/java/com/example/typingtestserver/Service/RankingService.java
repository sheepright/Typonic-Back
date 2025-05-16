package com.example.typingtestserver.Service;

import com.example.typingtestserver.Dto.RankingRequestDto;
import com.example.typingtestserver.Entity.Ranking;
import com.example.typingtestserver.Repository.RankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RankingService {
  @Autowired
  private RankingRepository repository;

  public String saveRanking(RankingRequestDto dto) {
    Optional<Ranking> existing = repository.findByEmail(dto.getEmail());
    Ranking ranking;
    String message;

    if (existing.isPresent()) {
      // 이미 있는 경우 → 이메일 빼고 업데이트
      ranking = existing.get();
      message = "업데이트 완료";
    } else {
      // 새로 저장
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

    repository.save(ranking);
    return message;
  }

  public double getPercentile(double wpm) {
    long total = repository.count();
    long above = repository.countByWpmGreaterThan(wpm);
    return ((double) above / total) * 100;
  }

  public List<Ranking> getTop50() {
    return repository.findTop50ByOrderByWpmDescAccuracyDesc();
  }

  public Ranking findByEmail(String email) {
    return repository.findByEmail(email).orElse(null);
  }
}
