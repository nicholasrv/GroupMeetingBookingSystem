package com.example.GroupMeetingBookingSystem.service.impl;

import com.example.GroupMeetingBookingSystem.model.Booking;
import com.example.GroupMeetingBookingSystem.repository.BookingRepository;
import com.example.GroupMeetingBookingSystem.service.MeetingBookingSystem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService implements MeetingBookingSystem<Booking> {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Booking save(Booking booking) {
        if(booking != null){
            return bookingRepository.insert(booking);
        };
        return new Booking();
    }

    @Override
    public String update(Booking booking) {
        if (booking != null && bookingRepository.findById(booking.getId()).isPresent()){
            bookingRepository.save(booking);
            return "The requested booking was successfully updated!";
        }
        return "Sorry, but the requested booking couldn't be found on the database";
    }

    @Override
    public List<Booking> getAllResults() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> searchById(String id) {
        return bookingRepository.findById(id);
    }

    @Override
    public boolean delete(String id) {
        if(bookingRepository.findById(id).isPresent()){
            bookingRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
