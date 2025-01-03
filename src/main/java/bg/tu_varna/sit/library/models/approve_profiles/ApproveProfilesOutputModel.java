package bg.tu_varna.sit.library.models.approve_profiles;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ApproveProfilesOutputModel implements OperationOutput {
    private String firstName;
    private String lastName;
    private Long id;
    private String username;
    private String email;
    private LocalDate birthdate;
}
