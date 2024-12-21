package bg.tu_varna.sit.library.models.recommended_books;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class RecommendedBooksOutputModel implements OperationOutput {
    private List<RecommendedBooksData> recommendedBooks;
}
