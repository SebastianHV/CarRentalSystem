package mx.edu.j2se.hernandezv.CarRentalSystem.CarRentalService;

import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Booking;
import mx.edu.j2se.hernandezv.CarRentalSystem.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "bookingService")
public class BookingServiceImpl implements BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Override
    public Iterable<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
