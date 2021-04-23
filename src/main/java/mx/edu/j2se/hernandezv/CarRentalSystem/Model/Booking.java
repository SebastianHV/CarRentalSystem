package mx.edu.j2se.hernandezv.CarRentalSystem.Model;

import java.time.LocalDateTime;

public class Booking {
    private int reservationId;
    private User user;
    private Car car;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean retrieved;
    private int grandTotal;

}
