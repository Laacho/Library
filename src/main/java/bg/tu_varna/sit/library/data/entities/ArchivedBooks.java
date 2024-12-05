package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table(name = "archived_books")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ArchivedBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @PastOrPresent
    @Column(name = "archive_date",nullable = false)
    private LocalDate archiveDate;

}

