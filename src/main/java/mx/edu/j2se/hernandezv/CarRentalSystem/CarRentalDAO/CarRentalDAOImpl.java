package mx.edu.j2se.hernandezv.CarRentalSystem.CarRentalDAO;

import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Booking;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.Car;
import mx.edu.j2se.hernandezv.CarRentalSystem.Model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "carRentalDAO")
public class CarRentalDAOImpl implements CarRentalDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Iterable<Car> getBookedCarsByTime(LocalDateTime startTime, LocalDateTime endTime) throws Exception {
        Query q = entityManager.createQuery(
                "SELECT b.car FROM Booking b WHERE (b.startTime >= :sTime AND b.startTime <= :eTime) OR " +
                        "(b.endTime >= :sTime AND b.endTime <= :eTime) AND b.retrieved = 'N'");
        q.setParameter("sTime", startTime);
        q.setParameter("eTime", endTime);
        List<Car> bookedCars = q.getResultList();

        return bookedCars;
    }

    @Override
    public Booking carReservation(int userId, int carId, LocalDateTime startTime, LocalDateTime endTime) throws Exception {
        User userEntity = entityManager.find(User.class, userId);
        if (userEntity == null) throw new Exception("DAO.USER_NOT_FOUND");
        Car carEntity = entityManager.find(Car.class, carId);
        if (carEntity == null) throw new Exception("DAO.CAR_NOT_FOUND");

        int days = (int) ChronoUnit.DAYS.between(startTime, endTime);
        float totalPrice = carEntity.getPricePerDay() * days;

        Booking newBooking = new Booking(userEntity, carEntity, startTime, endTime, totalPrice, 'N');
        entityManager.persist(newBooking);
        entityManager.flush();

        return newBooking;
    }

    @Override
    public Booking getReservation(int reservationId) throws Exception {
        Booking bookingEntity = entityManager.find(Booking.class, reservationId);
        if (bookingEntity == null) throw new Exception("DAO_RESERVATION_NOT_FOUND");
        return bookingEntity;
    }

    @Override
    public Integer returnCar(Booking booking) throws Exception {
        Query q = entityManager.createQuery("UPDATE Booking b SET b.retrieved = 'Y' WHERE b.reservationId = :resId ");
        q.setParameter("resId", booking.getReservationId());
        Integer retCar = q.executeUpdate();
        return retCar;
    }


}
