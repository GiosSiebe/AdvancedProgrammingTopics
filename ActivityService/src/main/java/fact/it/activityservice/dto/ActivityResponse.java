package fact.it.activityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityResponse {
    private Long id;
    private String activity;
    private String description;
    private String category;
    private Integer duration;
    private String energyLevelRequired;
}
