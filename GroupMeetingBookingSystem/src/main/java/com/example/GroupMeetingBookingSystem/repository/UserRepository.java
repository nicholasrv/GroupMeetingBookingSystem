package com.example.GroupMeetingBookingSystem.repository;

import com.example.GroupMeetingBookingSystem.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {
}
