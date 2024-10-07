package fact.it.activityservice.service;

import fact.it.activityservice.dto.ActivityResponse;
import fact.it.activityservice.model.Activity;
import fact.it.activityservice.repository.ActivityRepository;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    @Transactional(readOnly = true)
    public List<ActivityResponse> getAllActivities() {
        return activityRepository.findAll().stream()
                .map(activity ->
                        ActivityResponse.builder()
                                .id(activity.getId())
                                .activity(activity.getActivity())
                                .description(activity.getDescription())
                                .category(activity.getCategory())
                                .duration(activity.getDuration())
                                .energyLevelRequired(activity.getEnergyLevelRequired())
                                .build()
                ).toList();
    }

    @Transactional(readOnly = true)
    public List<ActivityResponse> findByCategory(String category) {
        return activityRepository.findByCategory(category).stream()
                .map(activity ->
                        ActivityResponse.builder()
                                .id(activity.getId())
                                .activity(activity.getActivity())
                                .description(activity.getDescription())
                                .category(activity.getCategory())
                                .duration(activity.getDuration())
                                .energyLevelRequired(activity.getEnergyLevelRequired())
                                .build()
                ).toList();
    }

    @Transactional
    public ActivityResponse addActivity(Activity activity) {
        Activity savedActivity = activityRepository.save(activity);
        return ActivityResponse.builder()
                .id(savedActivity.getId())
                .activity(savedActivity.getActivity())
                .description(savedActivity.getDescription())
                .category(savedActivity.getCategory())
                .duration(savedActivity.getDuration())
                .energyLevelRequired(savedActivity.getEnergyLevelRequired())
                .build();
    }

    @Transactional
    public ActivityResponse updateActivity(Long id, Activity updatedActivity) {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        // Update existing activity details
        existingActivity.setActivity(updatedActivity.getActivity());
        existingActivity.setDescription(updatedActivity.getDescription());
        existingActivity.setCategory(updatedActivity.getCategory());
        existingActivity.setDuration(updatedActivity.getDuration());
        existingActivity.setEnergyLevelRequired(updatedActivity.getEnergyLevelRequired());

        Activity savedActivity = activityRepository.save(existingActivity);
        return ActivityResponse.builder()
                .id(savedActivity.getId())
                .activity(savedActivity.getActivity())
                .description(savedActivity.getDescription())
                .category(savedActivity.getCategory())
                .duration(savedActivity.getDuration())
                .energyLevelRequired(savedActivity.getEnergyLevelRequired())
                .build();
    }

    @Transactional
    public void deleteActivity(Long id) {
        Activity existingActivity = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));

        activityRepository.delete(existingActivity);
    }
}
