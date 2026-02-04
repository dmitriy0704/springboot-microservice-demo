package dev.folomkin.userservice.model.dto;

import java.util.UUID;

/**
 * DTO for {@link dev.folomkin.userservice.model.entity.UserEntity}
 */
public record CreateUserResponse(UUID id, String name, String email,
                                 String keycloakId) {
}