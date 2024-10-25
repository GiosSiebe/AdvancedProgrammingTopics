
package fact.it.activityservice;

import fact.it.activityservice.model.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class ActivityTest {

    private Activity activity;

    @BeforeEach
    void setUp() {
        activity = new Activity();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(activity);
        assertNull(activity.getId());
        assertNull(activity.getActivity());
        assertNull(activity.getDescription());
        assertNull(activity.getCategory());
        assertNull(activity.getDuration());
        assertNull(activity.getEnergyLevelRequired());
    }

    @Test
    void testAllArgsConstructor() {
        Activity activity = new Activity(1L, "Running", "Running in the park", "Exercise", 30, "High");

        assertEquals(1L, activity.getId());
        assertEquals("Running", activity.getActivity());
        assertEquals("Running in the park", activity.getDescription());
        assertEquals("Exercise", activity.getCategory());
        assertEquals(30, activity.getDuration());
        assertEquals("High", activity.getEnergyLevelRequired());
    }

    @Test
    void testSettersAndGetters() {
        activity.setId(2L);
        activity.setActivity("Swimming");
        activity.setDescription("Swimming in the pool");
        activity.setCategory("Exercise");
        activity.setDuration(45);
        activity.setEnergyLevelRequired("Medium");

        assertEquals(2L, activity.getId());
        assertEquals("Swimming", activity.getActivity());
        assertEquals("Swimming in the pool", activity.getDescription());
        assertEquals("Exercise", activity.getCategory());
        assertEquals(45, activity.getDuration());
        assertEquals("Medium", activity.getEnergyLevelRequired());
    }
}
