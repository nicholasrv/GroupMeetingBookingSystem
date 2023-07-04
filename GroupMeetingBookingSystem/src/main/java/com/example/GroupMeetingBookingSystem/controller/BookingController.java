package com.example.GroupMeetingBookingSystem.controller;

import com.example.GroupMeetingBookingSystem.dto.BookingDTO;
import com.example.GroupMeetingBookingSystem.model.Booking;
import com.example.GroupMeetingBookingSystem.model.MeetingRoom;
import com.example.GroupMeetingBookingSystem.model.UserEntity;
import com.example.GroupMeetingBookingSystem.repository.BookingRepository;
import com.example.GroupMeetingBookingSystem.repository.MeetingRoomRepository;
import com.example.GroupMeetingBookingSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MeetingRoomRepository meetingRoomRepository;

    // GET
    @GetMapping("/all")
    public ResponseEntity<?> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.size() > 0){
            return new ResponseEntity<List<Booking>>(bookings, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No bookings could be found.", HttpStatus.NOT_FOUND);
        }
    }

    // POST
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@RequestBody BookingDTO bookingDTO) {

        //Gets the user and the room
        UserEntity userEntity = userRepository.findById(bookingDTO.getIdUser()).orElse(null);
        MeetingRoom meetingRoom = meetingRoomRepository.findById(bookingDTO.getIdRoom()).orElse(null);

        //Creates the booking
        Booking booking = new Booking(bookingDTO.getStartTime(), bookingDTO.getEndTime(), userEntity, meetingRoom);

        assert meetingRoom != null;

        // checks if the selected meeting room is available
        if (meetingRoom.isAvailability()) {

            try {
                bookingRepository.save(booking);
                return new ResponseEntity<Booking>(booking, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<>("The requested Meeting Room is not available.", HttpStatus.BAD_REQUEST);
        }
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity updateBookingById(@PathVariable("id") String id, @RequestBody BookingDTO bookingDTO) {

        // get the booking by searching by its id
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (optionalBooking.isPresent()) {
            Booking existingBooking = optionalBooking.get();

            // get both room and user ids through the DTO
            Optional<MeetingRoom> optionalMeetingRoom = meetingRoomRepository.findById(bookingDTO.getIdRoom());
            Optional<UserEntity> optionalUserEntity = userRepository.findById(bookingDTO.getIdUser());

            if (optionalMeetingRoom.isPresent() && optionalUserEntity.isPresent()) {
                MeetingRoom meetingRoom = optionalMeetingRoom.get();
                UserEntity user = optionalUserEntity.get();

                // Update the booking with the requested/given User/Room:
                existingBooking.setMeetingRoom(meetingRoom);
                existingBooking.setUserEntity(user);

                // Update both the start/end times:
                existingBooking.setStartTime(bookingDTO.getStartTime());
                existingBooking.setEndTime(bookingDTO.getEndTime());

                //Save the updated object back to the repository:
                bookingRepository.save(existingBooking);

                return new ResponseEntity<>(existingBooking, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Meeting Room/User not found", HttpStatus.NOT_FOUND);
            }

        } else {
            return new ResponseEntity<>("Booking not found", HttpStatus.NOT_FOUND);
        }
    }

    // GET BY ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBookingById(@PathVariable("id") String id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        if(bookingOptional.isPresent()) {
            return new ResponseEntity<>(bookingOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Booking not found with id" + id, HttpStatus.NOT_FOUND);
        }
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBookingById(@PathVariable("id") String id) {
        try{
            bookingRepository.deleteById(id);
            return new ResponseEntity<>("The booking with id " + id + "has been successfully deleted from the database.", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}