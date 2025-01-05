package bg.tu_varna.sit.library.models.update_notifications_for_admin;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateNotificationsForAdminInputModel implements OperationInput {
    @NotNull(message = "Messages list must not be null")
    @Size(min = 1, message = "Messages list must contain at least one message")
    private List<
            @NotBlank(message = "Each message must not be blank")
            String> messages;
}
