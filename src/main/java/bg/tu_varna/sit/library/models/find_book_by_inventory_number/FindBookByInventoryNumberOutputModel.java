package bg.tu_varna.sit.library.models.find_book_by_inventory_number;

import bg.tu_varna.sit.library.data.entities.Book;
import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class FindBookByInventoryNumberOutputModel implements OperationOutput {

    private Book book;

}
