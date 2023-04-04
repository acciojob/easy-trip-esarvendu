package com.driver.controllers;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;


import org.springframework.stereotype.Repository;
@Repository
public class AirportRepository {
    private Map<Integer, Passenger> passengerMap;
    private Map<String, Airport> airportMap;
    private Map<Integer, Flight> flightMap;
    public AirportRepository() {
        this.passengerMap = new HashMap<>();
        this.airportMap = new HashMap<>();
        this.flightMap = new HashMap<>();
    }
    public void addPassenger(Passenger passenger) {
        if (!passengerMap.containsKey(passenger.getPassengerId())) {
            passengerMap.put(passenger.getPassengerId(), passenger);
        }
    }

    public Passenger getPassengerById(int passengerId) {
        return passengerMap.get(passengerId);
    }

    public List<Passenger> getAllPassengers() {
        return new ArrayList<>(passengerMap.values());
    }

    // Flight Repository Methods

    public void addFlight(Flight flight) {
        if (!flightMap.containsKey(flight.getFlightId())) {
            flightMap.put(flight.getFlightId(), flight);
        }
    }

    public Flight getFlightById(int flightId) {
        return flightMap.get(flightId);
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flightMap.values());
    }

    public List<Flight> getFlightsByCity(City city) {
        return flightMap.values().stream()
                .filter(flight -> flight.getToCity() == city)
                .collect(Collectors.toList());
    }

    public List<Flight> getFlightsByDate(Date flightDate) {
        return flightMap.values().stream()
                .filter(flight -> flight.getFlightDate().equals(flightDate))
                .collect(Collectors.toList());
    }

    // Airport Repository Methods

    public void addAirport(Airport airport) {
        if (!airportMap.containsKey(airport.getAirportName())) {
            airportMap.put(airport.getAirportName(), airport);
        }
    }

    public Airport getAirportByName(String airportName) {
        return airportMap.get(airportName);
    }

    public List<Airport> getAllAirports() {
        return new ArrayList<>(airportMap.values());
    }

    public Airport getAirportByCity(City city) {
        return airportMap.values().stream()
                .filter(airport -> airport.getCity() == city)
                .findFirst()
                .orElse(null);
    }

}

