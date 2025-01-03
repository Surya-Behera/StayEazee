package Authentication.SpringAuthJwt.Repositories;

import Authentication.SpringAuthJwt.Entities.Property;
import Authentication.SpringAuthJwt.Entities.RoomOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Long> {
    List<Property> findByOwner(RoomOwner owner);
}

