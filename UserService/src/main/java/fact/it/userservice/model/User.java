package fact.it.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
}
