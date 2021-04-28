package mx.edu.j2se.hernandezv.CarRentalSystem.Model;

import javax.persistence.*;

@Entity
@Table
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int carId;

    private String carMaker;
    private String carClass;
    private int year;
    private int pricePerDay;
    private char availability;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarMaker() {
        return carMaker;
    }

    public void setCarMaker(String carMaker) {
        this.carMaker = carMaker;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public char getAvailability() {
        return availability;
    }

    public void setAvailability(char availability) {
        this.availability = availability;
    }
}
