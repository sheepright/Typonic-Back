package com.example.typingtestserver.Controller;

import com.example.typingtestserver.Dto.Ranking.RankingRequestDto;
import com.example.typingtestserver.Entity.Ranking;
import com.example.typingtestserver.Service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DB API", description = "랭킹 등록 및 조회 등과 같은 DB 연결 API")
@RestController
@RequestMapping("/db")
public class RankingController {
    @Autowired
    private RankingService service;

    @Operation(
            summary = "DB에 사용자 정보 업로드",
            description = "DB에 사용자의 이름,이메일 등 정보를 입력하거나 업데이트하는 API"
    )
    @PostMapping("/ranking")
    public ResponseEntity<?> createRanking(@RequestBody RankingRequestDto dto) {
        String result = service.saveRanking(dto);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "상위 등급표시",
            description = "타자 결과 화면에 내 결과의 wpm 이 상위 몇 퍼센트인지 알려주는 API"
    )
    @GetMapping("/percentile")
    public ResponseEntity<Double> getPercentile(@RequestParam double wpm) {
        return ResponseEntity.ok(service.getPercentile(wpm));
    }

    @Operation(
            summary = "리더보드 (50명) 문장",
            description = "문장 랭킹 리더보드에서 상위 50개의 랭킹을 가져와주는 API"
    )
    @GetMapping("/top50/sentence")
    public ResponseEntity<List<Ranking>> getTop50Sentence() {
        return ResponseEntity.ok(service.getTop50Sentence());
    }

    @Operation(
            summary = "리더보드 (50명) 단어",
            description = "단어 랭킹 리더보드에서 상위 50개의 랭킹을 가져와주는 API"
    )
    @GetMapping("/top50/word")
    public ResponseEntity<List<Ranking>> getTop50Word() {
        return ResponseEntity.ok(service.getTop50Word());
    }

    @Operation(
            summary = "E-mail 중복 체크",
            description = "랭킹 등록 전에 이미 DB에 존재하는 이메일인지 확인해주는 API"
    )
    @GetMapping("/email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = service.checkEmailExists(email);
        return ResponseEntity.ok(exists);
    }
}
