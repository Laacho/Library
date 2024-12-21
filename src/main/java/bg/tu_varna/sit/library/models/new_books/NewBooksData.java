package bg.tu_varna.sit.library.models.new_books;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class NewBooksData {
    private Long bookId;
    private String title;
    private String pathToImage;
}
