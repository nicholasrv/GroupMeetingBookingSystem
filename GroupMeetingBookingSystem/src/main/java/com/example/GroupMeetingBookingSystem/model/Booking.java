package com.example.GroupMeetingBookingSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(startTime, booking.startTime) && Objects.equals(endTime, booking.endTime) && Objects.equals(userEntity, booking.userEntity) && Objects.equals(meetingRoom, booking.meetingRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startTime, endTime, userEntity, meetingRoom);
    }
}
