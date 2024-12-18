package bg.tu_varna.sit.library.models.search_user_by_username;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserByUsernameInputModel implements OperationInput {
    private String username;
}
