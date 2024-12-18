package bg.tu_varna.sit.library.models.promote_user;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class PromoteUserOutputModel implements OperationOutput {
    private String message;
}
