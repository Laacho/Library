package bg.tu_varna.sit.library.models.delete_user;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class DeleteUserInputModel implements OperationInput {
    @NotNull(message = "ID must not be null")
    @Positive(message = "ID must be a positive number")
    private Long userId;

    @NotBlank(message = "New email must not be blank")
    @Email(message = "New email must be a valid email address")
    private String email;
}
