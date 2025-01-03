package bg.tu_varna.sit.library.models.update_notifications_for_admin;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateNotificationsForAdminOutputModel implements OperationOutput {
    private String message;
}
