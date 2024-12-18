package bg.tu_varna.sit.library.models.notifications_view;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class AdminNotificationViewOutputModel implements OperationOutput {
    private List<String> messages;
}
