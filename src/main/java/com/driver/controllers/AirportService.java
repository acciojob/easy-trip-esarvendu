package com.driver.controllers;


import java.util.Date;
        import java.util.List;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import com.driver.controllers.AirportRepository;
        import com.driver.model.Airport;
        import com.driver.model.City;
        import com.driver.model.Flight;
        import com.driver.model.Passenger;

@Service
public class AirportService {

    @Autowired
    private AirportRepository airportRepository;

    public void addPassenger(Passenger passenger) {
        airportRepository.addPassenger(passenger);
    }

    public Passenger getPassengerById(int passengerId) {
        return airportRepository.getPassengerById(passengerId);
    }

    public List<Passenger> getAllPassengers() {
        return airportRepository.getAllPassengers();
    }

    public void addFlight(Flight flight) {
        airportRepository.addFlight(flight);
    }

    public Flight getFlightById(int flightId) {
        return airportRepository.getFlightById(flightId);
    }

    public List<Flight> getAllFlights() {
        return airportRepository.getAllFlights();
    }

    public List<Flight> getFlightsByCity(City city) {
        return airportRepository.getFlightsByCity(city);
    }

    public List<Flight> getFlightsByDate(Date flightDate) {
        return airportRepository.getFlightsByDate(flightDate);
    }

    public void addAirport(Airport airport) {
        airportRepository.addAirport(airport);
    }

    public Airport getAirportByName(String airportName) {
        return airportRepository.getAirportByName(airportName);
    }

    public List<Airport> getAllAirports() {
        return airportRepository.getAllAirports();
    }

    public Airport getAirportByCity(City city) {
        return airportRepository.getAirportByCity(city);
    }

}
