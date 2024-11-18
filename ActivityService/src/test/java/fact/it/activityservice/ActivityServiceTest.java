package fact.it.activityservice;

import fact.it.activityservice.dto.ActivityResponse;
import fact.it.activityservice.model.Activity;
import fact.it.activityservice.repository.ActivityRepository;
import fact.it.activityservice.service.ActivityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @InjectMocks
    private ActivityService activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Test
    public void testGetAllActivities() {
        // Arrange
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setActivity("Jogging");
        activity.setDescription("A light cardio exercise.");
        activity.setCategory("Fitness");
        activity.setDuration(30);
        activity.setEnergyLevelRequired("Medium");

        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity));

        // Act
        List<ActivityResponse> activities = activityService.getAllActivities();

        // Assert
        assertEquals(1, activities.size());
        assertEquals("Jogging", activities.get(0).getActivity());
        assertEquals("A light cardio exercise.", activities.get(0).getDescription());
        assertEquals("Fitness", activities.get(0).getCategory());
        assertEquals(30, activities.get(0).getDuration());
        assertEquals("Medium", activities.get(0).getEnergyLevelRequired());

        verify(activityRepository, times(1)).findAll();
    }

    @Test
    public void testFindByCategory() {
        // Arrange
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setActivity("Jogging");
        activity.setDescription("A light cardio exercise.");
        activity.setCategory("Fitness");
        activity.setDuration(30);
        activity.setEnergyLevelRequired("Medium");

        when(activityRepository.findByCategory("Fitness")).thenReturn(Arrays.asList(activity));

        // Act
        List<ActivityResponse> activities = activityService.findByCategory("Fitness");

        // Assert
        assertEquals(1, activities.size());
        assertEquals("Jogging", activities.get(0).getActivity());
        assertEquals("Fitness", activities.get(0).getCategory());

        verify(activityRepository, times(1)).findByCategory("Fitness");
    }

    @Test
    public void testAddActivity() {
        // Arrange
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setActivity("Yoga");
        activity.setDescription("A relaxing exercise.");
        activity.setCategory("Wellness");
        activity.setDuration(60);
        activity.setEnergyLevelRequired("Low");

        when(activityRepository.save(any(Activity.class))).thenReturn(activity);

        // Act
        ActivityResponse response = activityService.addActivity(activity);

        // Assert
        assertEquals("Yoga", response.getActivity());
        assertEquals("Wellness", response.getCategory());
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    public void testUpdateActivity() {
        // Arrange
        Activity existingActivity = new Activity();
        existingActivity.setId(1L);
        existingActivity.setActivity("Jogging");
        existingActivity.setDescription("A light cardio exercise.");
        existingActivity.setCategory("Fitness");
        existingActivity.setDuration(30);
        existingActivity.setEnergyLevelRequired("Medium");

        Activity updatedActivity = new Activity();
        updatedActivity.setActivity("Running");
        updatedActivity.setDescription("A more intense cardio exercise.");
        updatedActivity.setCategory("Fitness");
        updatedActivity.setDuration(45);
        updatedActivity.setEnergyLevelRequired("High");

        when(activityRepository.findById(1L)).thenReturn(Optional.of(existingActivity));
        when(activityRepository.save(any(Activity.class))).thenReturn(updatedActivity);

        // Act
        ActivityResponse response = activityService.updateActivity(1L, updatedActivity);

        // Assert
        assertEquals("Running", response.getActivity());
        assertEquals("A more intense cardio exercise.", response.getDescription());
        assertEquals(45, response.getDuration());
        verify(activityRepository, times(1)).findById(1L);
        verify(activityRepository, times(1)).save(existingActivity);
    }

    @Test
    public void testDeleteActivity() {
        // Arrange
        Activity activity = new Activity();
        activity.setId(1L);
        when(activityRepository.findById(1L)).thenReturn(Optional.of(activity));

        // Act
        activityService.deleteActivity(1L);

        // Assert
        verify(activityRepository, times(1)).findById(1L);
        verify(activityRepository, times(1)).delete(activity);
    }
}
