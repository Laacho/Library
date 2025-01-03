package bg.tu_varna.sit.library.models.change_username;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ChangeUsernameOutputModel implements OperationOutput {
    private String message;
}
