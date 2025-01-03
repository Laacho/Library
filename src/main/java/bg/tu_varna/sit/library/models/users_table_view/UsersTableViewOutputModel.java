package bg.tu_varna.sit.library.models.users_table_view;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class UsersTableViewOutputModel implements OperationOutput {
    private List<UsersData> usersData;
}
