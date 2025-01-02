package bg.tu_varna.sit.library.models.get_reader_profile;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BookDataForReader {
    private long id;
    private String title;
    private String pathToImage;
    private Set<AuthorDataForReader> authorDataForReaders;
    private String genre;
    private String publisher;
    private String isbn;
    private String inventoryNumber;
}
