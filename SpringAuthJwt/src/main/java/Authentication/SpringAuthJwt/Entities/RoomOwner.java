package Authentication.SpringAuthJwt.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_owners")
public class RoomOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String contactNumber;
    private String email;


    @JsonIgnore
    @OneToMany(mappedBy ="owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> properties;
}