package com.example.typingtestserver.Repository;

import com.example.typingtestserver.Entity.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RankingRepository extends JpaRepository <Ranking, Long> {
    long countByWpmGreaterThan(double wpm);
    long count();
    List<Ranking> findTop50ByClassificationOrderByWpmDescAccuracyDesc(int classification);
    Optional<Ranking> findByEmail(String email);
    Optional<Ranking> findByEmailAndClassification(String email, int classification);
    boolean existsByNameAndClassification(String name, int classification);
}
