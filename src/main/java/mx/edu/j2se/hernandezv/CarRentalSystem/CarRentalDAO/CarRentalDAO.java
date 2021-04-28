package mx.edu.j2se.hernandezv.CarRentalSystem.CarRentalDAO;

import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Booking;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Car;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface CarRentalDAO {
    public Iterable<Car> getBookedCarsByTime(LocalDateTime startTime, LocalDateTime endTime) throws Exception;
    public Booking carReservation(int userId, int carId, LocalDateTime startTime, LocalDateTime endTime) throws  Exception;
    public Booking getReservation(int reservationId) throws Exception;
    public Integer returnCar(Booking booking) throws Exception;
}
