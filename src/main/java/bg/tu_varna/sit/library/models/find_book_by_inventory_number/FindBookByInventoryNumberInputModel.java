package bg.tu_varna.sit.library.models.find_book_by_inventory_number;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FindBookByInventoryNumberInputModel implements OperationInput {

    private String inventoryNumber;

}
