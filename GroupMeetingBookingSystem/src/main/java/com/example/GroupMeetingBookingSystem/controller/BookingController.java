package com.example.GroupMeetingBookingSystem.controller;

import com.example.GroupMeetingBookingSystem.dto.BookingDTO;
import com.example.GroupMeetingBookingSystem.dto.UserDTO;
import com.example.GroupMeetingBookingSystem.model.Booking;
import com.example.GroupMeetingBookingSystem.model.MeetingRoom;
import com.example.GroupMeetingBookingSystem.model.UserEntity;
import com.example.GroupMeetingBookingSystem.repository.BookingRepository;
import com.example.GroupMeetingBookingSystem.repository.MeetingRoomRepository;
import com.example.GroupMeetingBookingSystem.repository.UserRepository;
import com.example.GroupMeetingBookingSystem.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Autowired
    EmailService emailService;

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

        // Gets the user email and prepares the booking confirmation message
        String recipientEmail = bookingDTO.getEmail();
        String subject = "Group Meeting System - Booking Confirmation";
        String content = "Dear User, your booking has been confirmed for room, " + meetingRoom.getRoomName() + " on the following date/timeframe: " + bookingDTO.getStartTime().toString() + bookingDTO.getEndTime().toString() + " . Thank you very much!";


        // checks if the selected meeting room is available
        if (meetingRoom.isAvailability()) {

            try {
                //save the booking
                bookingRepository.save(booking);

                //set the meeting room as unavailable from now on and update the object on the database
                meetingRoom.setAvailability(false);
                meetingRoomRepository.save(meetingRoom);

                //send the confirmation email to customer
                emailService.sendBookingConfirmationEmail(recipientEmail, subject, content);

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
