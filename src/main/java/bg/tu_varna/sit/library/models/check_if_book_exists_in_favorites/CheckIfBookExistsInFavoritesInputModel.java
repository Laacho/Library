package bg.tu_varna.sit.library.models.check_if_book_exists_in_favorites;

import bg.tu_varna.sit.library.models.CommonBooksProperties;
import bg.tu_varna.sit.library.models.base.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CheckIfBookExistsInFavoritesInputModel implements OperationInput {
    private CommonBooksProperties commonBooksProperties;
}
