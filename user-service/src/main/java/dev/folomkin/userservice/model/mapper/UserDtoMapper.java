package dev.folomkin.userservice.model.mapper;

import dev.folomkin.userservice.model.dto.CreateUserRequest;
import dev.folomkin.userservice.model.dto.CreateUserResponse;
import dev.folomkin.userservice.model.entity.UserEntity;

public class UserDtoMapper {

    public static UserEntity mapDtoToEntity(String keycloakID, CreateUserRequest dto) {
        return new UserEntity(
                keycloakID,
                dto.name(),
                dto.email()
        );
    }

    public static CreateUserResponse mapEntityToDto(UserEntity entity) {
        return new CreateUserResponse(
                entity.getId(),
                entity.getKeycloakId(),
                entity.getName(),
                entity.getEmail()
        );
    }
}
