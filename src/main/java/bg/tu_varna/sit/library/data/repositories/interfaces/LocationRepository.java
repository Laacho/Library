package bg.tu_varna.sit.library.data.repositories.interfaces;

import bg.tu_varna.sit.library.data.entities.Location;

import java.util.Optional;

public interface LocationRepository extends GenericRepository<Location> {
    Optional<Location> findByNameAndRow(String name,Long row);
}
