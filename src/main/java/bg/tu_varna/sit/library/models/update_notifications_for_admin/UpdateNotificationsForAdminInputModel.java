package bg.tu_varna.sit.library.models.update_notifications_for_admin;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateNotificationsForAdminInputModel implements OperationInput {
    private List<String> messages;
}
