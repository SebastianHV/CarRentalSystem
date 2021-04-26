package mx.edu.j2se.hernandezv.CarRentalSystem.WebController;

import mx.edu.j2se.hernandezv.CarRentalSystem.CarRentalService.BookingService;
import mx.edu.j2se.hernandezv.CarRentalSystem.CarRentalService.CarRentalService;
import mx.edu.j2se.hernandezv.CarRentalSystem.CarRentalService.UserService;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Booking;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Car;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class WebControl {

    @Autowired
    UserService userService;

    @Autowired
    BookingService bookingService;

    @Autowired
    CarRentalService carRentalService;

    @Autowired
    Environment environment;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping(path = "/all_users")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/all_bookings")
    public @ResponseBody Iterable<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

//    @RequestMapping(value = "/availableCars", params = {"carClass", "startTime", "endTime"}, method = RequestMethod.GET)
    @GetMapping("/availableCars")
    @ResponseBody
    public ResponseEntity<Iterable<Car>> getAvailableCars(@RequestParam("startTime")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startTime,
                                          @RequestParam("endTime")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endTime,
                                          @RequestParam(required = false) String carClass) throws Exception {
        ResponseEntity<Iterable<Car>> responseEntity = null;
        Iterable<Car> carList = null;
        try {
            if (carClass == null) carList = carRentalService.getAvailableCars(startTime, endTime);
            carList = carRentalService.getAvailableCars(startTime , endTime, carClass);
            responseEntity = new ResponseEntity<Iterable<Car>>(carList, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = environment.getProperty(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, errorMessage);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/reservation/{reservationId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Booking> getReservationById(@PathVariable("reservationId") int reservationId) throws Exception {
        ResponseEntity<Booking> responseEntity = null;
        try {
            Booking responseReservation = carRentalService.getReservationById(reservationId);
            responseEntity = new ResponseEntity<Booking>(responseReservation, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = environment.getProperty(e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, errorMessage);
        }

        return  responseEntity;
    }
}
