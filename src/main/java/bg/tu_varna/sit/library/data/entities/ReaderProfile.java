package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Date;

@Table(name = "reader_profile")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReaderProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PastOrPresent
    @Column(nullable = false,name = "borrowing_date")
    private LocalDate borrowingDate;
    @PastOrPresent
    @Column(nullable = false,name = "return_date")
    private LocalDate returnDate;
    @PastOrPresent
    @Column(nullable = false,name = "return_deadline")
    private LocalDate returnDeadline;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}

