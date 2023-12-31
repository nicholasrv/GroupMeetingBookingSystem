package com.example.GroupMeetingBookingSystem.repository;

import com.example.GroupMeetingBookingSystem.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUsername(String username);
    public Boolean existsByUsername(String username);
}
