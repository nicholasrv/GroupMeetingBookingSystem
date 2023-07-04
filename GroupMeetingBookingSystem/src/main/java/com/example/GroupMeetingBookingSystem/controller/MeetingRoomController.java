package com.example.GroupMeetingBookingSystem.controller;

import com.example.GroupMeetingBookingSystem.model.MeetingRoom;
import com.example.GroupMeetingBookingSystem.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class MeetingRoomController {

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    // GET THE AVAILABLE ROOMS
    @GetMapping("/available")
    public ResponseEntity<?> getAllAvailableRooms() {

        List<MeetingRoom> rooms = meetingRoomRepository.findAll();
        List<MeetingRoom> availableRooms = new ArrayList<>();

        //iterates over the list to check those which are currently available and then adds it to an array list.
        for(MeetingRoom room : rooms) {
            if (room.isAvailability()) {
                availableRooms.add(room);
            }
        }

        // conditional statement to return the response with all the available rooms or the not found status with a message.
        if (!availableRooms.isEmpty()){
            return new ResponseEntity<List<MeetingRoom>>(availableRooms, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Sorry, but there are no rooms available.", HttpStatus.NOT_FOUND);
        }

    }

    // GET ALL ROOMS
    @GetMapping("/all")
    public ResponseEntity<?> getAllRooms() {
        List<MeetingRoom> meetingRooms = meetingRoomRepository.findAll();
        if (meetingRooms.size() > 0){
            return new ResponseEntity<List<MeetingRoom>>(meetingRooms, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No rooms could be found.", HttpStatus.NOT_FOUND);
        }
    }


    // POST
    @PostMapping("/create")
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
