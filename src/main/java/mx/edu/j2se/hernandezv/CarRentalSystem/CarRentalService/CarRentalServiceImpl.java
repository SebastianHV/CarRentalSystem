package mx.edu.j2se.hernandezv.CarRentalSystem.CarRentalService;

import mx.edu.j2se.hernandezv.CarRentalSystem.CarRentalDAO.CarRentalDAO;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Booking;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Car;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.User;
import mx.edu.j2se.hernandezv.CarRentalSystem.Repository.BookingRepository;
import mx.edu.j2se.hernandezv.CarRentalSystem.Repository.CarRepository;
import mx.edu.j2se.hernandezv.CarRentalSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "carRentalService")
@Transactional
public class CarRentalServiceImpl implements CarRentalService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    CarRentalDAO carRentalDAO;


    @Override
    public Iterable<Car> getAvailableCars(LocalDateTime startTime, LocalDateTime endTime, String carClass) throws Exception{
        List<Car> carList = new ArrayList<>();
//        List<Booking> reservationList = new ArrayList<>();
        List<Car> reservedCarList = new ArrayList<>();

        carList = carRepository.findAll();
//        reservationList = bookingRepository.findAll();

//        reservedCarList = reservationList.stream().filter(booking ->
//                ( ( startTime.isAfter(booking.getStartTime()) && startTime.isBefore(booking.getEndTime()) )
//                || ( endTime.isAfter(booking.getStartTime()) && endTime.isBefore(booking.getEndTime()) )
//                || startTime.isEqual(booking.getStartTime()) || startTime.isEqual(booking.getEndTime())
//                || endTime.isEqual(booking.getStartTime()) || endTime.isEqual(booking.getEndTime()) )
//                && ( booking.getRetrieved() == 'N' )
//
//        ).map(booking -> booking.getCar()).collect(Collectors.toList());

//        reservedCarList = (List<Car>) getBookedCarsByTime(startTime, endTime);
        reservedCarList = (List<Car>) carRentalDAO.getBookedCarsByTime(startTime, endTime);

        List<Car> returnCarList = new ArrayList<>();
        carList.removeAll(reservedCarList);

        carList = carList.stream().filter(car -> car.getCarClass().equals(carClass)).collect(Collectors.toList());

        if (carList.isEmpty()) throw new Exception("Service.EMPTY_CAR_LIST");
        return carList;
    }

    @Override
    public Iterable<Car> getAvailableCars(LocalDateTime startTime, LocalDateTime endTime) throws Exception {
        List<Car> carList = new ArrayList<>();
        carList = carRepository.findAll();

        List<Car> reservedCarList = new ArrayList<>();
//        reservedCarList = (List<Car>) getBookedCarsByTime(startTime, endTime);
        reservedCarList = (List<Car>) carRentalDAO.getBookedCarsByTime(startTime, endTime);

        List<Car> returnCarList = new ArrayList<>();
        carList.removeAll(reservedCarList);

        if (carList.isEmpty()) throw new Exception("Service.EMPTY_CAR_LIST");
        return carList;
    }

    @Override
    public Booking getReservationById(int reservationId) throws Exception {
        Optional<Booking> reservation = bookingRepository.findById(reservationId);
        if (reservation.isPresent()) return reservation.get();
        throw new Exception("Service.RESERVATION_NOT_FOUND");
    }

    @Override
    public Booking bookCar(int userId, int carId, LocalDateTime startTime, LocalDateTime endTime) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user;
        if (optionalUser.isPresent())
            user = optionalUser.get();
        else
            throw new Exception("DAO.USER_NOT_FOUND");

        Optional<Car> optionalCar = carRepository.findById(carId);
        Car car;
        if (optionalCar.isPresent())
            car = optionalCar.get();
        else
            throw new Exception("DAO.CAR_NOT_FOUND");

        List<Car> bookedCars = new ArrayList<>();
        bookedCars = (List<Car>) getBookedCarsByTime(startTime, endTime);

        if (car.getAvailability() == 'Y' && bookedCars.contains(car) == false){
//            int days = (int) ChronoUnit.DAYS.between(startTime, endTime);
//            float totalPrice = car.getPricePerDay() * days;
//            Booking newBooking = new Booking(user, car, startTime, endTime, totalPrice, 'N');
//            newBooking = bookingRepository.save(newBooking);
//            return newBooking;
            Booking newReservation = carRentalDAO.carReservation(userId, carId, startTime, endTime);
            return newReservation;
        } else
            throw new Exception("Service.CAR_UNAVAILABLE");
    }

    @Override
    public Integer returnCar(Booking reservation) throws Exception {
        Booking currentReservation = carRentalDAO.getReservation(reservation.getReservationId());
        if (currentReservation.getRetrieved() == 'Y') throw new Exception("Service.CAR_ALREADY_RETURNED");
        Integer returnedCar = carRentalDAO.returnCar(reservation);
        if (returnedCar == 0) throw new Exception("DAO.UPDATE_WENT_WRONG");
        return returnedCar;
    }

    public Iterable<Car> getBookedCarsByTime(LocalDateTime startTime, LocalDateTime endTime){
        List<Booking> bookedCarsList = new ArrayList<>();
        bookedCarsList = bookingRepository.findAll();

        List<Car> filteredList = new ArrayList<>();
        filteredList = bookedCarsList.stream().filter(booking ->
                ( ( booking.getStartTime().isAfter(startTime) && booking.getStartTime().isBefore(endTime) )
                        || ( booking.getEndTime().isAfter(startTime) && booking.getEndTime().isBefore(endTime) )
                        || startTime.isEqual(booking.getStartTime()) || startTime.isEqual(booking.getEndTime())
                        || endTime.isEqual(booking.getStartTime()) || endTime.isEqual(booking.getEndTime()) )
                        && ( booking.getRetrieved() == 'N' )

        ).map(booking -> booking.getCar()).collect(Collectors.toList());

        return filteredList;
    }
}
