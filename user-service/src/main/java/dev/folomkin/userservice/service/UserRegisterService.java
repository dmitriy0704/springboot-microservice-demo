package dev.folomkin.userservice.service;

import dev.folomkin.userservice.model.entity.UserEntity;
import dev.folomkin.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRegisterService {

    private final UserRepository userRepository;

    public UserRegisterService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity create(UserEntity user) {
        return userRepository.save(user);
    }


    public List<UserEntity> getAlUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserByKeycloakId(String keycloakId) {
        return userRepository.findByKeycloakId(keycloakId);
    }
}
