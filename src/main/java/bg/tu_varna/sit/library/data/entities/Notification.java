package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import jakarta.persistence.*;


@Table(name = "notifications")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String message;
    @Column(nullable = false)
    private Boolean isAdmin;
    @Column(nullable = false)
    private Boolean isRead;
    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;
}
