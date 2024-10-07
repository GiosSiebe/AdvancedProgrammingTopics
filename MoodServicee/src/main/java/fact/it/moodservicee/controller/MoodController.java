package fact.it.moodservicee.controller;

import fact.it.moodservicee.dto.MoodRequest;
import fact.it.moodservicee.dto.MoodResponse;
import fact.it.moodservicee.service.MoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mood")
@RequiredArgsConstructor
public class MoodController {

    private final MoodService moodService;

    // Create a new mood entry
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createMood(@RequestBody MoodRequest moodRequest) {
        moodService.createMood(moodRequest);
    }

    // Get all mood entries based on timestamp
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MoodResponse> getAllMoodsByTimestamp(@RequestParam LocalDateTime timestamp) {
        return moodService.getAllMoodsByTimestamp(timestamp);
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<MoodResponse> getAllMoodsByUser(@PathVariable String id) {
        return moodService.getAllMoodsByUser(id);
    }

    // Get all mood entries
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MoodResponse> getAllMoods() {
        return moodService.getAllMoods();
    }

    // Update an existing mood entry by ID
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMood(@PathVariable String id, @RequestBody MoodRequest moodRequest) {
        moodService.updateMood(id, moodRequest);
    }

    // Delete a mood entry by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // Returns 204 No Content on successful deletion
    public void deleteMood(@PathVariable String id) {
        moodService.deleteMood(id);
    }
}
