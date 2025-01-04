package bg.tu_varna.sit.library.models.find_book_by_inventory_number;

import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FindBookByInventoryNumberInputModel implements OperationInput {
    @NotBlank(message = "Inventory number must not be blank")
    @Size(max = 50, message = "Inventory number must not exceed 50 characters")
    private String inventoryNumber;

}
