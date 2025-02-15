package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;

@Table(name = "user_credentials")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserCredentials {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean verified;
    @Column(name = "verification_code")
    private String verificationCode;
    @FutureOrPresent
    private LocalDate dateOfVerification;
    @Email
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Boolean admin;
    @Column()
    @Min(value = 0)
    @Max(value = 10)
    @Digits(integer = 1, fraction = 1)
    private Double rating;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_to_cart",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> cartForBooks;
}
