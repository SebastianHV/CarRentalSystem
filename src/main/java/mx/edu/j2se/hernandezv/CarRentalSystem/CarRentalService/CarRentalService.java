package mx.edu.j2se.hernandezv.CarRentalSystem.CarRentalService;

import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Booking;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Car;

import java.time.LocalDateTime;

public interface CarRentalService {

    public Iterable<Car> getAvailableCars(LocalDateTime startTime, LocalDateTime endTime, String carClass) throws Exception;
    public Iterable<Car> getAvailableCars(LocalDateTime startTime, LocalDateTime endTime) throws Exception;
    public Booking getReservationById(int reservationId) throws Exception;
    public Booking bookCar(int userId, int carId, LocalDateTime startTime, LocalDateTime endTime) throws Exception;
    public Integer returnCar(Booking reservation) throws Exception;

}
