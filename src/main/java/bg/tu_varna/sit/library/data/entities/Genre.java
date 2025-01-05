package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import jakarta.persistence.*;


@Table(name = "genre")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false)
    private String name;

}

