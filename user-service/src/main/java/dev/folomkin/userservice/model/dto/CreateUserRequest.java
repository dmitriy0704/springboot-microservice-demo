package dev.folomkin.userservice.model.dto;

import dev.folomkin.userservice.model.entity.UserEntity;

/**
 * DTO for {@link UserEntity}
 */
public record CreateUserRequest(
        String name,
        String email
) {
}