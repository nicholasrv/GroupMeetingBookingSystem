package com.example.GroupMeetingBookingSystem.repository;

import com.example.GroupMeetingBookingSystem.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(String name);
}
