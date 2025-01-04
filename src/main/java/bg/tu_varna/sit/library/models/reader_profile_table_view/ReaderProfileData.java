package bg.tu_varna.sit.library.models.reader_profile_table_view;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
public class ReaderProfileData {
    private String username;
    private boolean isApproved;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
