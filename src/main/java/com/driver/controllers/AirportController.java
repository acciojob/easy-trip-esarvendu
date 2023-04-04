package com.driver.controllers;


import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class AirportController {
    HashMap<String,Airport> airportHashMap = new HashMap<>();
    HashMap<Integer,Flight> flightHashMap = new HashMap<>();
    HashMap<Integer,Passenger> passengerHashMap = new HashMap<>();

    HashMap<Integer,Integer> passengerFlightMap = new HashMap<>();
    @PostMapping("/add_airport")
    public String addAirport(@RequestBody Airport airport){

        String name = airport.getAirportName();
        airportHashMap.put(name,airport);

        //Simply add airport details to your database
        //Return a String message "SUCCESS"

        return "SUCCESS";
    }

    @GetMapping("/get-largest-aiport")
    public String getLargestAirportName(){

        String name = "";
        int noOfAirport = 0;

        for(Map.Entry<String,Airport> entry : airportHashMap.entrySet()){
            if(entry.getValue().getNoOfTerminals()>noOfAirport){
                noOfAirport = entry.getValue().getNoOfTerminals();
                name = entry.getKey();
            }
            else if(entry.getValue().getNoOfTerminals()==noOfAirport){
                int result = name.compareTo(entry.getKey());
                if (result > 0) {
                    name = entry.getKey();
                }
            }
        }
        return name;

        //Largest airport is in terms of terminals. 3 terminal airport is larger than 2 terminal airport
        //Incase of a tie return the Lexicographically smallest airportName

    }

    @GetMapping("/get-shortest-time-travel-between-cities")
    public double getShortestDurationOfPossibleBetweenTwoCities(@RequestParam("fromCity") City fromCity, @RequestParam("toCity")City toCity){
        double duration = Integer.MAX_VALUE;
        for(Map.Entry<Integer,Flight> entry : flightHashMap.entrySet()){
            if(entry.getValue().getFromCity().equals(fromCity) && entry.getValue().getToCity().equals(toCity)){
                if(entry.getValue().getDuration()<duration){
                    duration = entry.getValue().getDuration();
                }
            }
        }
        //Find the duration by finding the shortest flight that connects these 2 cities directly
        //If there is no direct flight between 2 cities return -1.

        return duration;
    }

    @GetMapping("/get-number-of-people-on-airport-on/{date}")
    public int getNumberOfPeopleOn(@PathVariable("date") Date date,@RequestParam("airportName")String airportName){
        int count = 0;
        for(Map.Entry<Integer,Integer> entry : passengerFlightMap.entrySet()){
//            if()
        }

        //Calculate the total number of people who have flights on that day on a particular airport
        //This includes both the people who have come for a flight and who have landed on an airport after their flight

        return 0;
    }

    @GetMapping("/calculate-fare")
    public int calculateFlightFare(@RequestParam("flightId")Integer flightId){
        int fare = 0;
        int count = 0;
        for(Map.Entry<Integer,Integer> entry : passengerFlightMap.entrySet()){
            if(entry.getValue().equals(flightId)){
                count++;
            }
        }
        fare = 3000 + count*50;
        //Calculation of flight prices is a function of number of people who have booked the flight already.
        //Price for any flight will be : 3000 + noOfPeopleWhoHaveAlreadyBooked*50
        //Suppose if 2 people have booked the flight already : the price of flight will be 3000 + 2*50 = 3100
        //This will not include the current person who is trying to book, he might also be just checking price

        return fare;

    }


    @PostMapping("/book-a-ticket")
    public String bookATicket(@RequestParam("flightId")Integer flightId,@RequestParam("passengerId")Integer passengerId){
        int count = 0;
        for(Map.Entry<Integer,Integer> entry : passengerFlightMap.entrySet()){
            if(entry.getKey().equals(passengerId) && entry.getValue().equals(flightId)){
                return "FAILURE";
            }
            else if(entry.getValue().equals(flightId)){
                count++;
            }
        }
        if(count>flightHashMap.get(flightId).getMaxCapacity()){
            return "FAILURE";
        }

        passengerFlightMap.put(passengerId,flightId);
        //If the numberOfPassengers who have booked the flight is greater than : maxCapacity, in that case :
        //return a String "FAILURE"
        //Also if the passenger has already booked a flight then also return "FAILURE".
        //else if you are able to book a ticket then return "SUCCESS"

        return "SUCCESS";
    }

    @PutMapping("/cancel-a-ticket")
    public String cancelATicket(@RequestParam("flightId")Integer flightId,@RequestParam("passengerId")Integer passengerId){
        if(!passengerFlightMap.containsKey(passengerId) || !passengerFlightMap.get(passengerId).equals(flightId)){
            return "FAILURE";
        }
        passengerFlightMap.remove(passengerId);
        //If the passenger has not booked a ticket for that flight or the flightId is invalid or in any other failure case
        // then return a "FAILURE" message
        // Otherwise return a "SUCCESS" message
        // and also cancel the ticket that passenger had booked earlier on the given flightId

        return "SUCCESS";
    }


    @GetMapping("/get-count-of-bookings-done-by-a-passenger/{passengerId}")
    public int countOfBookingsDoneByPassengerAllCombined(@PathVariable("passengerId")Integer passengerId){
        int count = 0;
        for(Map.Entry<Integer,Integer> entry : passengerFlightMap.entrySet()){
            if(Objects.equals(entry.getKey(), passengerId)){
                count++;
            }
        }
        //Tell the count of flight bookings done by a passenger: This will tell the total count of flight bookings done by a passenger :
        return count;
    }

    @PostMapping("/add-flight")
    public String addFlight(@RequestBody Flight flight){
        flightHashMap.put(flight.getFlightId(),flight);
        //Return a "SUCCESS" message string after adding a flight.
        return "SUCCESS";
    }


    @GetMapping("/get-aiportName-from-flight-takeoff/{flightId}")
    public String getAirportNameFromFlightId(@PathVariable("flightId")Integer flightId){
        Flight flight = flightHashMap.get(flightId);
        City city = flight.getFromCity();
        String airportName = "";
        for(Map.Entry<String,Airport> entry : airportHashMap.entrySet()){
            if(entry.getValue().getCity().equals(city)){
                airportName = entry.getKey();
                break;
            }
        }
        //We need to get the starting airportName from where the flight will be taking off (Hint think of City variable if that can be of some use)
        //return null incase the flightId is invalid or you are not able to find the airportName

        return airportName;
    }


    @GetMapping("/calculate-revenue-collected/{flightId}")
    public int calculateRevenueOfAFlight(@PathVariable("flightId")Integer flightId){
        int count = 0;
        for(Map.Entry<Integer,Integer> entry : passengerFlightMap.entrySet()){
            if(entry.getValue().equals(flightId)){
                count++;
            }
        }
        int totalRevenue = 0;
        for(int i=0; i<count; i++){
            totalRevenue += 3000 + (i * 50);
        }

        //Calculate the total revenue that a flight could have
        //That is of all the passengers that have booked a flight till now what will be the revenue of the same


        return totalRevenue;
    }


    @PostMapping("/add-passenger")
    public String addPassenger(@RequestBody Passenger passenger){
        int id = passenger.getPassengerId();
        passengerHashMap.put(id,passenger);
        //Add a passenger to the database
        //And return a "SUCCESS" message
        return "SUCCESS";
    }


}
