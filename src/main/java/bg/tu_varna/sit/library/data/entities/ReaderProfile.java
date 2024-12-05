package bg.tu_varna.sit.library.data.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "readers_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ReaderProfile implements Serializable {
    @Id
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reader_profile_to_books", joinColumns = @JoinColumn(name = "reader_profile_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> favoriteBooks;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reader_profile_to_want_to_read", joinColumns = @JoinColumn(name = "reader_profile_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> wantToRead;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reader_profile_to_read_books", joinColumns = @JoinColumn(name = "reader_profile_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> readBooks;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reader_profile_to_authors", joinColumns = @JoinColumn(name = "reader_profile_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> favoriteAuthors;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reader_profile_to_genre", joinColumns = @JoinColumn(name = "reader_profile_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> favoriteGenres;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", updatable = false)
    private LocalDateTime updatedAt;
}
