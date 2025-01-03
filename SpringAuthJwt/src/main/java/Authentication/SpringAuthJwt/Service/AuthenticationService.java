package Authentication.SpringAuthJwt.Service;

import Authentication.SpringAuthJwt.Config.JwtService;
import Authentication.SpringAuthJwt.Entities.Owner;
import Authentication.SpringAuthJwt.Repositories.OwnerRepository;
import Authentication.SpringAuthJwt.Entities.Role;
import Authentication.SpringAuthJwt.Entities.User;
import Authentication.SpringAuthJwt.Repositories.UserRepository;
import Authentication.SpringAuthJwt.Entities.AuthenticationRequest;
import Authentication.SpringAuthJwt.Entities.AuthenticationResponse;
import Authentication.SpringAuthJwt.Entities.PropertyDetailsRequest;
import Authentication.SpringAuthJwt.Entities.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final OwnerRepository ownerRepository;
    //    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .fullname(request.getFullname())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
//        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse registerowner(RegisterRequest request) {
        var newOwner = Owner.builder()
                .fullname(request.getFullname())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .location(request.getLocation())
                .role(Role.USER)
                .build();
        var savedUser = ownerRepository.save(newOwner);
        var jwtToken = jwtService.generateToken(newOwner);
        var refreshToken = jwtService.generateRefreshToken(newOwner);
//        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void updatePropertyDetails(String email, PropertyDetailsRequest request) {
        var owner = ownerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));

        // Update the fields with the new values
        owner.setRoomQuantity(request.getRoomQuantity());
        owner.setPreferredTenants(request.getPreferredTenants());
        owner.setAvailability(request.getAvailability());
        owner.setBathroomType(request.getBathroomType());
        owner.setArea(request.getArea());
        owner.setFloor(request.getFloor());
        owner.setLocation(request.getLocation());
        owner.setPrice(request.getPrice());

        // Save the updated owner
        ownerRepository.save(owner);
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }
    public AuthenticationResponse authenticateOwner(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var newowner = ownerRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(newowner);
        var refreshToken = jwtService.generateRefreshToken(newowner);
//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Owner findOwnerDetails(String email) {
        return ownerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
    }

    public List<Owner> findAllOwnerDetails() {
        return ownerRepository.findAll();
    }

//    private void saveUserToken(User user, String jwtToken) {
//        var token = Token.builder()
//                .user(user)
//                .token(jwtToken)
//                .tokenType(TokenType.BEARER)
//                .expired(false)
//                .revoked(false)
//                .build();
//        tokenRepository.save(token);
//    }
//
//    private void revokeAllUserTokens(User user) {
//        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
//    }

//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        userEmail = jwtService.extractUsername(refreshToken);
//        if (userEmail != null) {
//            var user = this.repository.findByEmail(userEmail)
//                    .orElseThrow();
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                var authResponse = AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }

}
