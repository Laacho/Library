package bg.tu_varna.sit.library.data.entities;

import bg.tu_varna.sit.library.data.enums.BookStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Set;

@Table(name = "borrowed_books")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BorrowedBooks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @PastOrPresent
    @Column(nullable = false, name = "borrowing_date")
    private LocalDate borrowingDate;
    @PastOrPresent
    @Column(nullable = false, name = "return_date")
    private LocalDate returnDate;
    @PastOrPresent
    @Column(name = "return_deadline")
    private LocalDate returnDeadline;
    @Column(name = "is_request_approved")
    private Boolean isRequestApproved;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "borrowed_books_to_books",
            joinColumns = @JoinColumn(name = "borrowed_book_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> borrowedBooks;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}

