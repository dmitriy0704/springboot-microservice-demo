package dev.folomkin.userservice.repository;

import dev.folomkin.userservice.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

Optional<UserEntity>  findByKeycloakId(String keycloakId);
}
