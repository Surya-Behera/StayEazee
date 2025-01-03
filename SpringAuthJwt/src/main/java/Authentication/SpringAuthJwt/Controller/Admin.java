package Authentication.SpringAuthJwt.Controller;

import Authentication.SpringAuthJwt.Config.JwtService;
import Authentication.SpringAuthJwt.Service.AuthenticationService;
import Authentication.SpringAuthJwt.Entities.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class Admin {
    @Autowired
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    @GetMapping("/owner-details")
    public ResponseEntity<List<Owner>> getAllOwnerDetails() {
        List<Owner> owners = authenticationService.findAllOwnerDetails();
        return ResponseEntity.ok(owners);
    }
}
