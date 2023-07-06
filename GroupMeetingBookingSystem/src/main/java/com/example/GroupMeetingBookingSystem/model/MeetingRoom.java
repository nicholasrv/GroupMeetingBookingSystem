package com.example.GroupMeetingBookingSystem.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Document
public class MeetingRoom {
    @Id
    private String id;

    private String roomName;
    private String maxAllowance;
    private String availableResources;
    public boolean availability;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingRoom that = (MeetingRoom) o;
        return availability == that.availability && Objects.equals(id, that.id) && Objects.equals(roomName, that.roomName) && Objects.equals(maxAllowance, that.maxAllowance) && Objects.equals(availableResources, that.availableResources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomName, maxAllowance, availableResources, availability);
    }
}
