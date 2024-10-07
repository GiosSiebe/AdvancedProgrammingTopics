package fact.it.activityservice.repository;

import fact.it.activityservice.model.Activity;
import jakarta.transaction.Transactional;
import jdk.jfr.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.accessibility.AccessibleIcon;
import java.util.List;

@Repository
@Transactional
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByCategory(String category);
}
