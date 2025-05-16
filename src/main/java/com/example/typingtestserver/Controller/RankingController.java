package com.example.typingtestserver.Controller;

import com.example.typingtestserver.Dto.RankingRequestDto;
import com.example.typingtestserver.Dto.EmailCheckDto;
import com.example.typingtestserver.Entity.Ranking;
import com.example.typingtestserver.Service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingController {
  @Autowired
  private RankingService service;

  //데이터들을 DB에 저장 (존재시 email 빼고 업데이트)
  @PostMapping
  public ResponseEntity<?> createRanking(@RequestBody RankingRequestDto dto){
    return ResponseEntity.ok(service.saveRanking(dto));
  }

  //wpm을 파라미터로 입력하면 DB에서 입력된 wpm은 상위 몇퍼센트인지 조회
  @GetMapping("/percentile")
  public ResponseEntity<Double> getPercentile(@RequestParam double wpm){
    return ResponseEntity.ok(service.getPercentile(wpm));
  }

  //DB에서 top 50위를 조회
  @GetMapping("/top50")
  public ResponseEntity<List<Ranking>> getTop50(){
    return ResponseEntity.ok(service.getTop50());
  }

  //이메일을 파라미터로 입력하면 이름과 wpm을 조회
  @GetMapping("/email")
  public ResponseEntity<?> getRankingByEmail(@RequestParam String email) {
    Ranking ranking = service.findByEmail(email);
    if (ranking != null) {
      return ResponseEntity.ok(new EmailCheckDto(ranking.getName(), ranking.getWpm()));
    } else {
      return ResponseEntity.status(404).body("DB에 해당 이메일이 없습니다.");
    }
  }
}
