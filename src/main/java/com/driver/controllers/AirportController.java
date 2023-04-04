package com.driver.controllers;

import com.driver.controllers.AirportRepository;
import com.driver.controllers.AirportService;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController

public class AirportController {
    private Map<String, Airport> airports = new HashMap<>();
    private Map<Integer, Flight> flights = new HashMap<>();
    private Map<Integer, Passenger> passengers = new HashMap<>();
    private Map<Integer, List<Integer>> flightPassengers = new HashMap<>();
    @PostMapping("/add_airport")
    public String addAirport(@RequestBody Airport airport){

        //Simply add airport details to your database
        //Return a String message "SUCCESS"

            airports.put(airport.getName(), airport);
        return "SUCCESS";
    }

    @GetMapping("/get-largest-aiport")
    @Autowired
    public String getLargestAirportName(){

        //Largest airport is in terms of terminals. 3 terminal airport is larger than 2 terminal airport
        //Incase of a tie return the Lexicographically smallest airportName
        int maxTerminals = 0;
        String largestAirportName = null;
        for (Airport airport : airports.values()) {
            if (airport.GetnoOfTerminals() > maxTerminals) {
                maxTerminals = airport.getNoOfTerminals();
                largestAirportName = airport.getName();
            } else if (airport.getNoOfTerminals() == maxTerminals) {
                if (largestAirportName == null || airport.getName().compareTo(largestAirportName) < 0) {
                    largestAirportName = airport.getName();
                }
            }
        }
       return null;
    }

    @GetMapping("/get-shortest-time-travel-between-cities")
    public double getShortestDurationOfPossibleBetweenTwoCities(@RequestParam("fromCity") City fromCity, @RequestParam("toCity")City toCity){

        //Find the duration by finding the shortest flight that connects these 2 cities directly
        //If there is no direct flight between 2 cities return -1.
        double shortestDuration = -1;
        for (Flight flight : flights.values()) {
            if (flight.getFromCity() == fromCity && flight.getToCity() == toCity) {
                if (shortestDuration == -1 || flight.getDuration() < shortestDuration) {
                    shortestDuration = flight.getDuration();
                }
            }
        }
       return 0;
    }

    @GetMapping("/get-number-of-people-on-airport-on/{date}")
    public int getNumberOfPeopleOn(@PathVariable("date") Date date,@RequestParam("airportName")String airportName){

        //Calculate the total number of people who have flights on that day on a particular airport
        //This includes both the people who have come for a flight and who have landed on an airport after their flight
        int count = 0;
        for (Integer flightId : flights.keySet()) {
            Flight flight = flights.get(flightId);
            if (flight.getDepartureDate().equals(date) && (flight.getFromAirport().equals(airportName) || flight.getToAirport().equals(airportName))) {
                if (flightPassengers.containsKey(flightId)) {
                    count += flightPassengers.get(flightId).size();
                }
            }
        }
        return 0;
    }

    @GetMapping("/calculate-fare")
    public int calculateFlightFare(@RequestParam("flightId")Integer flightId){

        //Calculation of flight prices is a function of number of people who have booked the flight already.
        //Price for any flight will be : 3000 + noOfPeopleWhoHaveAlreadyBooked*50
        //Suppose if 2 people have booked the flight already : the price of flight for the third person will be 3000 + 2*50 = 3100
        //This will not include the current person who is trying to book, he might also be just checking price
        int noOfPeopleWhoHaveAlreadyBooked = 0;
        if (flightPassengers.containsKey(flightId)) {
            noOfPeopleWhoHaveAlreadyBooked = flightPassengers.get(flightId).size();
        }
        return 3000 + noOfPeopleWhoHaveAlreadyBooked*50;

    }


    @PostMapping("/book-a-ticket")
    public String bookATicket(@RequestParam("flightId")Integer flightId,@RequestParam("passengerId")Integer passengerId){

        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"

        return null;
    }

    @PutMapping("/cancel-a-ticket")
    public String cancelATicket(@RequestParam("flightId")Integer flightId,@RequestParam("passengerId")Integer passengerId){

        //If the passenger has not booked a ticket for that flight or the flightId is invalid or in any other failure case
        // then return a "FAILURE" message
        // Otherwise return a "SUCCESS" message
        // and also cancel the ticket that passenger had booked earlier on the given flightId

       return null;
    }


    @GetMapping("/get-count-of-bookings-done-by-a-passenger/{passengerId}")
    public int countOfBookingsDoneByPassengerAllCombined(@PathVariable("passengerId")Integer passengerId){

        //Tell the count of flight bookings done by a passenger: This will tell the total count of flight bookings done by a passenger :
        int count = 0;
        for (Flight flight : flights) {
            for (Passenger passenger : flight.getPassengers()) {
                if (Objects.equals(passenger.getPassengerId(), passengerId)) {
                    count++;
                }
            }
        }
        return count;
    }

    @PostMapping("/add-flight")
    public String addFlight(@RequestBody Flight flight){

        //Return a "SUCCESS" message string after adding a flight.
        //Return a "SUCCESS" message string after adding a flight.
        flights.add(flight);
        return "SUCCESS";
    }


    @GetMapping("/get-aiportName-from-flight-takeoff/{flightId}")
    public String getAirportNameFromFlightId(@PathVariable("flightId")Integer flightId){

        //We need to get the starting airportName from where the flight will be taking off (Hint think of City variable if that can be of some use)
        //return null incase the flightId is invalid or you are not able to find the airportName

        return null;
    }


    @GetMapping("/calculate-revenue-collected/{flightId}")
    public int calculateRevenueOfAFlight(@PathVariable("flightId")Integer flightId){

        //Calculate the total revenue that a flight could have
        //That is of all the passengers that have booked a flight till now and then calculate the revenue
        //Revenue will also decrease if some passenger cancels the flight


        return 0;
    }


    @PostMapping("/add-passenger")
    public String addPassenger(@RequestBody Passenger passenger){

        //Add a passenger to the database
        //And return a "SUCCESS" message if the passenger has been added successfully.

       return null;
    }


}


