package fact.it.activityservice.controller;

import fact.it.activityservice.dto.ActivityResponse;
import fact.it.activityservice.model.Activity;
import fact.it.activityservice.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity")
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

    // Add a new activity
    @PostMapping
    public ResponseEntity<ActivityResponse> addActivity(@RequestBody Activity activity) {
        ActivityResponse createdActivity = activityService.addActivity(activity);
        return ResponseEntity.status(201).body(createdActivity);
    }

    // Update an existing activity
    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable Long id, @RequestBody Activity updatedActivity) {
        ActivityResponse activity = activityService.updateActivity(id, updatedActivity);
        return ResponseEntity.ok(activity);
    }

    // Delete an activity
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        activityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}
