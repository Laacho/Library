package bg.tu_varna.sit.library.data.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "is_profile_approved")
    private Boolean isProfileApproved;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reader_profile_to_books", joinColumns = @JoinColumn(name = "reader_profile_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> favoriteBooks;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reader_profile_to_want_to_read", joinColumns = @JoinColumn(name = "reader_profile_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> wantToRead;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reader_profile_to_read_books", joinColumns = @JoinColumn(name = "reader_profile_id"), inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> readBooks;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "reader_profile_to_genre", joinColumns = @JoinColumn(name = "reader_profile_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> recommendedGenres;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", updatable = false)
    private LocalDateTime updatedAt;
}
