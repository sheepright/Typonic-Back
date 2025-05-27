package com.example.typingtestserver.Repository;

import com.example.typingtestserver.Entity.OpenAi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<OpenAi, Long> {
}
