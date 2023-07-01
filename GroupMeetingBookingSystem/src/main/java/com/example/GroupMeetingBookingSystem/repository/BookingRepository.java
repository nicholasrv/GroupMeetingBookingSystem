package com.example.GroupMeetingBookingSystem.repository;

import com.example.GroupMeetingBookingSystem.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
