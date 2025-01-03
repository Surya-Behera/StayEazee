package Authentication.SpringAuthJwt.Controller;

import Authentication.SpringAuthJwt.Config.JwtService;
import Authentication.SpringAuthJwt.Service.AuthenticationService;
import Authentication.SpringAuthJwt.Entities.PropertyDetailsRequest;
import Authentication.SpringAuthJwt.Entities.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo-controller")

public class DemoController {
    @Autowired
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public ResponseEntity<String> sayHello() {

        return ResponseEntity.ok("Hello from secured endpoint");
    }

    @PostMapping("/property-details")
    public ResponseEntity<Void> addPropertyDetails(
            @RequestBody PropertyDetailsRequest request,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        // Extract the token, assuming it's in the format "Bearer token"
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token); // Implement this method to extract username

        // Update property details for the owner
        authenticationService.updatePropertyDetails(email, request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/owner-details")
    public ResponseEntity<Owner> getOwnerDetails(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader)
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = authHeader.substring(7);
        String email = jwtService.extractUsername(token);

        Owner owner = authenticationService.findOwnerDetails(email);
        return ResponseEntity.ok(owner);
    }



}

