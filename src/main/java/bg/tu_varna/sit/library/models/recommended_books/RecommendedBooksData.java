package bg.tu_varna.sit.library.models.recommended_books;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class RecommendedBooksData {
    private String title;
    private String pathToImage;
    private Long id;
}
