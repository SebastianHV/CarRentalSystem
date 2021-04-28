package mx.edu.j2se.hernandezv.CarRentalSystem.Model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private float totalPrice;
    private char retrieved;

    public Booking(){}

    public Booking(User user, Car car, LocalDateTime startTime, LocalDateTime endTime, float totalPrice, char retrieved) {
        this.user = user;
        this.car = car;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.retrieved = retrieved;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public char getRetrieved() {
        return retrieved;
    }

    public void setRetrieved(char retrieved) {
        this.retrieved = retrieved;
    }
}
