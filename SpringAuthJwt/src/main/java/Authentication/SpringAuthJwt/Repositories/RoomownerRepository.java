package Authentication.SpringAuthJwt.Repositories;


import Authentication.SpringAuthJwt.Entities.RoomOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomownerRepository extends JpaRepository<RoomOwner,Long> {
}
