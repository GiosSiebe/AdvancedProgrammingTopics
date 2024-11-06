package fact.it.recommendationservice.controller;

import fact.it.recommendationservice.dto.MoodResponse;
import fact.it.recommendationservice.dto.RecommendationRequest;
import fact.it.recommendationservice.service.MoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/recommendation/mood")
@RequiredArgsConstructor
public class MoodController {

    private final MoodService moodService;

    // Get all mood entries
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MoodResponse> getAllMoods() {
        return moodService.getAllMoods();
    }

}
