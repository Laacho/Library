package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Table(name = "discarded_books")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class DiscardedBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @PastOrPresent
    @Column(nullable = false,name = "discarding_date")
    private LocalDate discardingDate;

    @Column(nullable = false)
    private String reason;

}
