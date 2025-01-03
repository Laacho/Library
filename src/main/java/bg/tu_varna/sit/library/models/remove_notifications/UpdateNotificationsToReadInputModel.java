package bg.tu_varna.sit.library.models.remove_notifications;

import bg.tu_varna.sit.library.models.base.OperationInput;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateNotificationsToReadInputModel implements OperationInput {
    private List<String> notifications;
}
