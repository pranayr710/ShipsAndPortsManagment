package time;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PortTimeRecord {
    private String shipName;
    private String portName;
    private LocalDateTime arrivalTime;		//date-time without a time-zone(Stores the date and time when the ship arrives at the port.
    private LocalDateTime departureTime;

    // Existing constructor used by recordShipArrival() with scanner input
    public PortTimeRecord(String shipName, LocalDateTime arrivalTime) {
        this.shipName = shipName;
        this.portName = "Unknown"; // Default value if port not provided
        this.arrivalTime = arrivalTime;
    }
    
    // ADDED: Overloaded constructor accepting portName
    public PortTimeRecord(String shipName, String portName, LocalDateTime arrivalTime) {
        this.shipName = shipName;
        this.portName = portName;
        this.arrivalTime = arrivalTime;
    }

    public String getShipName() {
        return shipName;
    }
    
    public String getPortName() {
        return portName;
    }

    // Set the departure time when the ship leaves
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    // Calculate port stay duration
    public Duration getPortStayDuration() {
        if (arrivalTime != null && departureTime != null) {
            return Duration.between(arrivalTime, departureTime);
        }
        return Duration.ZERO;
    }

    // Return formatted arrival time
    public String getFormattedArrivalTime(String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return arrivalTime.format(formatter);
    }

    // Return formatted departure time
    public String getFormattedDepartureTime(String pattern) {
        if (departureTime == null) {
            return "Not departed yet";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return departureTime.format(formatter);
    }

    @Override
    public String toString() {
        String arrival = getFormattedArrivalTime("yyyy-MM-dd HH:mm:ss");
        String departure = (departureTime == null) ? "N/A" : getFormattedDepartureTime("yyyy-MM-dd HH:mm:ss");
        return "Ship: " + shipName + " at Port: " + portName + ", Arrival: " + arrival + ", Departure: " + departure;
    }
}
