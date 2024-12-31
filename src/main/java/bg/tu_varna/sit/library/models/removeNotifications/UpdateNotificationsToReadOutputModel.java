package bg.tu_varna.sit.library.models.removeNotifications;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateNotificationsToReadOutputModel implements OperationOutput {
    private String message;
}
