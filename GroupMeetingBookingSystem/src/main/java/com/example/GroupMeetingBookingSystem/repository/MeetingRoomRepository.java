package com.example.GroupMeetingBookingSystem.repository;

import com.example.GroupMeetingBookingSystem.model.MeetingRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MeetingRoomRepository extends MongoRepository<MeetingRoom, String> {

}
