package bg.tu_varna.sit.library.models.approve_profiles;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ApproveProfilesInputModel implements OperationInput {
    private String username;
}
