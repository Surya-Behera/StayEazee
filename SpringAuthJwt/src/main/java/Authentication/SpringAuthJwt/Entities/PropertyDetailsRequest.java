package Authentication.SpringAuthJwt.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDetailsRequest {
    private String roomQuantity;
    private String preferredTenants;
    private String availability;
    private String bathroomType;
    private String area;
    private String floor;
    private String location;
    private int price;
}
