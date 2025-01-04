package bg.tu_varna.sit.library.models.update_notifications_for_admin;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
