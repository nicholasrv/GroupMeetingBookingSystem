package com.example.GroupMeetingBookingSystem.service.impl;

import com.example.GroupMeetingBookingSystem.model.Booking;
import com.example.GroupMeetingBookingSystem.model.MeetingRoom;
import com.example.GroupMeetingBookingSystem.repository.MeetingRoomRepository;
import com.example.GroupMeetingBookingSystem.service.MeetingBookingSystem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingRoomService implements MeetingBookingSystem<MeetingRoom> {

    private final MeetingRoomRepository meetingRoomRepository;

    public MeetingRoomService(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }


    @Override
    public MeetingRoom save(MeetingRoom meetingRoom) {
        if(meetingRoom != null){
            return meetingRoomRepository.insert(meetingRoom);
        };
        return new MeetingRoom();
    }

    @Override
    public String update(MeetingRoom meetingRoom) {
        if (meetingRoom != null && meetingRoomRepository.findById(meetingRoom.getId()).isPresent()){
            meetingRoomRepository.save(meetingRoom);
            return "The requested meeting room was successfully updated!";
        }
        return "Sorry, but the requested meeting room couldn't be found on the database";
    }

    @Override
    public List<MeetingRoom> getAllResults() {
        return meetingRoomRepository.findAll();
    }

    @Override
    public Optional<MeetingRoom> searchById(String id) {
        return meetingRoomRepository.findById(id);
    }

    @Override
    public boolean delete(String id) {
        if(meetingRoomRepository.findById(id).isPresent()){
            meetingRoomRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
