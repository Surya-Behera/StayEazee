package Authentication.SpringAuthJwt.Entities;

import Authentication.SpringAuthJwt.Entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String fullname;
    private String phone;
    private String email;
    private String password;
    private String location;
    private Role role;
}
