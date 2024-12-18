package bg.tu_varna.sit.library.models.delete_user;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class DeleteUserOutputModel implements OperationOutput {
    private String message;
}
