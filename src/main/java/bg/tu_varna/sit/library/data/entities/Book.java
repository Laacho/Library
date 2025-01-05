package bg.tu_varna.sit.library.data.entities;

import bg.tu_varna.sit.library.data.enums.BookStatus;
import lombok.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import java.util.Set;

@Table(name = "books")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, unique = true, name = "inventory_number")
    private String inventoryNumber;
    @Column(nullable = false)
    @Digits(integer = 3, fraction = 2)
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "author_to_books",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors;


    @Column(nullable = false)
    private String path;
    @Enumerated(EnumType.STRING)
    @Column(name = "book_status_before_borrow")
    private BookStatus bookStatusBeforeBorrow;
    @Enumerated(EnumType.STRING)
    @Column(name = "book_status_after_borrow")
    private BookStatus bookStatusAfterBorrow;
}
