package bg.tu_varna.sit.library.models.demote_user;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class DemoteUserOutputModel implements OperationOutput {
    private String message;
}
