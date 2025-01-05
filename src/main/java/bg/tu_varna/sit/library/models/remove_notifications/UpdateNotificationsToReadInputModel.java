package bg.tu_varna.sit.library.models.remove_notifications;

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
public class UpdateNotificationsToReadInputModel implements OperationInput {
    @NotNull(message = "Notifications list must not be null")
    @Size(min = 1, message = "Notifications list must contain at least one notification")
    private List<
            @NotBlank(message = "Each notification must not be blank")
            String> notifications;
}
