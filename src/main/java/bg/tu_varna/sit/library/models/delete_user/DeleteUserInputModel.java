package bg.tu_varna.sit.library.models.delete_user;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class DeleteUserInputModel implements OperationInput {
    private Long userId;
    private String email;
}
