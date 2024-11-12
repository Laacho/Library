package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
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
    @Column(nullable = false)
    private Boolean admin;
    @Column()
    @Min(value = 0)
    @Max(value = 10)
    @Digits(integer = 1, fraction = 1)
    private Double rating;
    @OneToOne(mappedBy = "user")
    private UserCredentials userCredentials;

}

