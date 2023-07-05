package com.example.GroupMeetingBookingSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Booking {
    @Id
    public String id;

    public LocalDateTime startTime;
    public LocalDateTime endTime;
    @DBRef
    public UserEntity userEntity;
    @DBRef
    public MeetingRoom meetingRoom;

    public Booking(LocalDateTime startTime, LocalDateTime endTime, UserEntity userEntity, MeetingRoom meetingRoom) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.userEntity = userEntity;
        this.meetingRoom = meetingRoom;
    }
}
