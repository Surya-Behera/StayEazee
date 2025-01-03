package Authentication.SpringAuthJwt.auth;

import Authentication.SpringAuthJwt.Entities.AuthenticationRequest;
import Authentication.SpringAuthJwt.Entities.AuthenticationResponse;
import Authentication.SpringAuthJwt.Entities.RegisterRequest;
import Authentication.SpringAuthJwt.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }


    @PostMapping("/register/owner")
    public ResponseEntity<AuthenticationResponse> registerOwner(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerowner(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/authenticate/owner")
    public ResponseEntity<AuthenticationResponse> authenticateowner(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticateOwner(request));
    }

}

