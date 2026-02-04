package dev.folomkin.userservice.controller;

import dev.folomkin.userservice.model.dto.CreateUserRequest;
import dev.folomkin.userservice.model.dto.CreateUserResponse;
import dev.folomkin.userservice.model.dto.UpdateUserProfileDto;
import dev.folomkin.userservice.model.entity.UserEntity;
import dev.folomkin.userservice.repository.UserRepository;
import dev.folomkin.userservice.service.UserRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/user")
public class UserRegisterController {

    private final UserRegisterService userRegisterService;

    private final UserRepository repository;

    public UserRegisterController(UserRegisterService userRegisterService, UserRepository repository) {
        this.userRegisterService = userRegisterService;
        this.repository = repository;
    }

    @GetMapping("/me")
    public UserEntity me(@AuthenticationPrincipal Jwt jwt) {
        String keycloakId = jwt.getSubject();
        return userRegisterService.getUserByKeycloakId(keycloakId)
                .orElseGet(() -> createFromJwt(jwt));
    }


    @PostMapping("/register")
    public UserEntity createFromJwt(Jwt jwt) {
        UserEntity user = new UserEntity();
        user.setKeycloakId(jwt.getSubject());
        user.setEmail(jwt.getClaimAsString("email"));
        user.setName(jwt.getClaimAsString("preferred_username"));
        return userRegisterService.create(user);
    }


    @GetMapping("/allusers")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userRegisterService.getAlUsers());
    }


    @PutMapping("/me")
    public UserEntity updateMe(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody UpdateUserProfileDto dto) {
        String keycloakId = jwt.getSubject();
        UserEntity user = repository.findByKeycloakId(keycloakId)
                .orElseThrow(() -> new IllegalStateException("User not found"));
        applyUpdates(user, dto);
        return repository.save(user);
    }


    private void applyUpdates(UserEntity user, UpdateUserProfileDto dto) {
        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        if (dto.getCity() != null) {
            user.setCity(dto.getCity());
        }
        if (dto.getAddress() != null) {
            user.setAddress(dto.getAddress());
        }
    }
}
