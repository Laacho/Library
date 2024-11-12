package bg.tu_varna.sit.library.data.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name = "authors")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> books;


}
