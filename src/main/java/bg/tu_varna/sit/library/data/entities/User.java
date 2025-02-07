package bg.tu_varna.sit.library.data.entities;

import lombok.*;
import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import java.time.LocalDate;

@Table(name = "users")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Past(message = "date must be in the past")
    private LocalDate birthdate;
    @OneToOne(mappedBy = "user")
    private UserCredentials userCredentials;

}

