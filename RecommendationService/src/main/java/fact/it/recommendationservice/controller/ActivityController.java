package fact.it.recommendationservice.controller;

import fact.it.recommendationservice.dto.ActivityResponse;
import fact.it.recommendationservice.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    // Get all activities
    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities() {
        List<ActivityResponse> activities = activityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    // Get activities by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ActivityResponse>> findByCategory(@PathVariable String category) {
        List<ActivityResponse> activities = activityService.findByCategory(category);
        return ResponseEntity.ok(activities);
    }

}
