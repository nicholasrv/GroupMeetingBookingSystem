package com.example.GroupMeetingBookingSystem.controller;

import com.example.GroupMeetingBookingSystem.dto.BookingDTO;
import com.example.GroupMeetingBookingSystem.dto.MeetingRoomDTO;
import com.example.GroupMeetingBookingSystem.model.MeetingRoom;
import com.example.GroupMeetingBookingSystem.repository.MeetingRoomRepository;
import com.example.GroupMeetingBookingSystem.service.impl.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class MeetingRoomController {

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    // GET
    @GetMapping("/get")
    public ResponseEntity<?> getAllRooms() {
        List<MeetingRoom> rooms = meetingRoomRepository.findAll();
        if (rooms.size() > 0){
            return new ResponseEntity<List<MeetingRoom>>(rooms, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("There are no rooms available", HttpStatus.NOT_FOUND);
        }
    }

    // POST
    @PostMapping("/new")
    public ResponseEntity<?> createRoom(@RequestBody MeetingRoom meetingRoom) {
        try{
            meetingRoomRepository.save(meetingRoom);
            return new ResponseEntity<MeetingRoom>(meetingRoom, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity updateMeetingRoomById(@PathVariable("id") String id, @RequestBody MeetingRoom meetingRoom){
        Optional<MeetingRoom> idMeetingRoom = meetingRoomRepository.findById(id);
        if (idMeetingRoom.isPresent()) {
            MeetingRoom meetingRoomToBeUpdated = idMeetingRoom.get();
            meetingRoomToBeUpdated.setRoomName(meetingRoom.getRoomName() != null ? meetingRoom.getRoomName() : meetingRoomToBeUpdated.getRoomName());
            meetingRoomToBeUpdated.setMaxAllowance(meetingRoom.getMaxAllowance() != null ? meetingRoom.getMaxAllowance() : meetingRoomToBeUpdated.getMaxAllowance());
            meetingRoomToBeUpdated.setAvailableResources(meetingRoom.getAvailableResources() != null ? meetingRoom.getAvailableResources() : meetingRoomToBeUpdated.getAvailableResources());
            meetingRoomRepository.save(meetingRoom);
            return new ResponseEntity<>(meetingRoomToBeUpdated, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("No meeting rooms were found with id " + id, HttpStatus.NOT_FOUND);
        }
    }

    // GET BY ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable("id") String id) {
        Optional<MeetingRoom> meetingRoomOptional = meetingRoomRepository.findById(id);
        if(meetingRoomOptional.isPresent()) {
            return new ResponseEntity<>(meetingRoomOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Meeting Room not found with id" + id, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoomById(@PathVariable("id") String id) {
        try{
            meetingRoomRepository.deleteById(id);
            return new ResponseEntity<>("The room with id " + id + "has been successfully deleted from the database.", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
