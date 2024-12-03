package bg.tu_varna.sit.library.models.admin_home_view;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class AdminHomeViewOutputModel implements OperationOutput {
    private int countUsers;
    private int countNotifications;
    private int countAllBooks;
    private int countArchivedBooks;
    private int countDiscardedBooks;
    private int countReadersProfiles;
}
