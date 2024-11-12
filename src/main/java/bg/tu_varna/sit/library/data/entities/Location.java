package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Table(name = "locations")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String shelf;

    @Column(nullable = false,name = "row_num")
    @Min(value = 0)
    private Long rowNum;

}

