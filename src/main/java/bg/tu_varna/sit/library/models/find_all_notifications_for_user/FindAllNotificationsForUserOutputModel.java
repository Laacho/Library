package bg.tu_varna.sit.library.models.find_all_notifications_for_user;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FindAllNotificationsForUserOutputModel implements OperationOutput {
    private List<String> notifications;
}
