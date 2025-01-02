package bg.tu_varna.sit.library.models.get_reader_profile;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AuthorDataForReader {
    private long id;
    private String firstName;
    private String lastName;
}
