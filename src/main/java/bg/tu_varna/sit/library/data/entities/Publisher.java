package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import jakarta.persistence.*;


@Table(name = "publisher")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
}
