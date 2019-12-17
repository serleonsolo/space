package com.space.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name="ship")
public class Ship {

    private static final Integer CURRENT_YEAR = 3019;
    private static final Integer MAX_NAME_LENGTH = 50;
    private static final Integer MAX_PLANET_LENGTH = 50;
    private static final Double IS_USED_FALSE_CF = 1d;
    private static final Double IS_USED_TRUE_CF = 0.5d;
    private static final Integer RATING_CF = 80;
    private static final Double MIN_SPEED = 0.01d;
    private static final Double MAX_SPEED = 0.99d;
    private static final Integer MIN_CREW_SIZE = 1;
    private static final Integer MAX_CREW_SIZE = 9999;
    private static final Integer MIN_YEAR = 2800;
    private static final Integer MAX_YEAR = 3019;
    private static final Boolean DEFAULT_IS_USED = false;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String planet;

    @Enumerated(EnumType.STRING)
    private ShipType shipType;

    private Date prodDate;

    private Boolean isUsed;

    private Double speed;

    private Integer crewSize;

    private Double rating;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlanet() {
        return planet;
    }

    public ShipType getShipType() {
        return shipType;
    }

    public Date getProdDate() {
        return prodDate;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public Double getSpeed() {
        return speed;
    }

    public Integer getCrewSize() {
        return crewSize;
    }

    public Double getRating() {
        return rating;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public void setUsed(Boolean used){ isUsed = used; }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void calculate()
    {
        calculateIsUsed();
        calculateRating();
    }

    private void calculateIsUsed()
    {
        if(isUsed == null)
            isUsed = DEFAULT_IS_USED;
    }

    private void calculateRating()
    {
        Double cf = isUsed ? IS_USED_TRUE_CF : IS_USED_FALSE_CF;
        Double rating = RATING_CF * speed * cf / (CURRENT_YEAR - getYear(prodDate) + 1);
        this.rating = roundDouble(rating);
    }

    public boolean isValid()
    {
        if(name == null || planet == null || shipType == null ||
           prodDate == null || speed == null || crewSize == null)
            return false;
        if(name.length() > MAX_NAME_LENGTH || planet.length() > MAX_PLANET_LENGTH)
            return false;
        if(name.equals("") || planet.equals(""))
            return false;
        Double roundedSpeed = roundDouble(speed);
        if(roundedSpeed < MIN_SPEED || roundedSpeed > MAX_SPEED)
            return false;
        if(crewSize < MIN_CREW_SIZE || crewSize > MAX_CREW_SIZE)
            return false;
        if(prodDate.getTime() < 0 || getYear(prodDate) < MIN_YEAR || getYear(prodDate) > MAX_YEAR)
            return false;
        return true;
    }

    public static Double roundDouble(Double input)
    {
        return Math.round(input * 100.0) / 100.0;
    }

    public static Integer getYear(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ship ship = (Ship) o;
        return id.equals(ship.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ship{" +
                "id=" + id +
                '}';
    }
}
