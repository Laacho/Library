package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "genre")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,updatable = false)
    private String name;

}

