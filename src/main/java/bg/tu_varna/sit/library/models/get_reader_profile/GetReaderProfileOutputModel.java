package bg.tu_varna.sit.library.models.get_reader_profile;

import bg.tu_varna.sit.library.models.base.OperationOutput;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetReaderProfileOutputModel implements OperationOutput {
    private String username;
    private Map<String,List<BookDataForReader>> recommendedGenres;
    private List<BookDataForReader> wantsToRead;
    private List<BookDataForReader> favouriteBook;
    private List<BookDataForReader> readBooks;

}
