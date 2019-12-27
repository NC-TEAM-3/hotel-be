package com.netcracker.hotelbe.service;

import com.netcracker.hotelbe.entity.ApartmentClass;
import com.netcracker.hotelbe.entity.Booking;
import com.netcracker.hotelbe.entity.User;
import com.netcracker.hotelbe.repository.BookingRepository;
import com.netcracker.hotelbe.service.filter.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;


@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ApartmentClassService apartmentClassService;

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    UserService userService;

    @Autowired
    private FilterService filterService;

    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    public List<Booking> getAllByParams(Map<String, String> allParams) {
        if(allParams.size()!=0) {
            return bookingRepository.findAll(filterService.fillFilter(allParams, Booking.class));
        } else {
            return bookingRepository.findAll();
        }
    }

    public Booking save(final Booking booking) {
        final ApartmentClass apartmentClass = apartmentClassService.findById(booking.getApartmentClass().getId());
        booking.setApartmentClass(apartmentClass);
        final User user = userService.findById(booking.getUser().getId());
        booking.setUser(user);
        return bookingRepository.save(booking);
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(id))
        );
    }

    public Booking update(Booking booking, Long id) {

        bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(id))
        );

        ApartmentClass apartmentClass = apartmentClassService.findById(booking.getApartmentClass().getId());

        User user = userService.findById(booking.getUser().getId());

        booking.setApartmentClass(apartmentClass);
        booking.setUser(user);
        booking.setId(id);

        return bookingRepository.save(booking);
    }

    public void deleteById(Long id) {
        Booking delete = bookingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.valueOf(id))
        );

        bookingRepository.delete(delete);
    }
}
