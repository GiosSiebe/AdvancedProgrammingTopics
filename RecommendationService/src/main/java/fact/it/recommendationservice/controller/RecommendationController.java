package fact.it.recommendationservice.controller;

import fact.it.recommendationservice.dto.RecommendationResponse;
import fact.it.recommendationservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<RecommendationResponse> getRecommendations(@PathVariable String userId) {
        return recommendationService.getRecommendationsByUserId(userId);
    }
}
