package Ships;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import time.PortTimeRecord;

public class PortManager implements PortOperations {
    private Scanner scanner;
    private ArrayList<Ports> ports;
    private ArrayList<PortTimeRecord> timeRecords;

    public PortManager(ArrayList<Ports> ports) {
        // Initialize the ports list, the scanner for user input, and the list for time records
        this.ports = ports;
        this.scanner = new Scanner(System.in);
        this.timeRecords = new ArrayList<>();
    }

    public void registerPort() {
        try {
            System.out.println("\n--- Register New Port ---");
            // Prompt user for port name and location
            System.out.print("Enter port name: ");
            String name = scanner.nextLine();
            System.out.print("Enter port location: ");
            String location = scanner.nextLine();
            // Get port ID and container capacity from the user
            int id = getIntInput("Enter port ID: ", 1, Integer.MAX_VALUE);
            int cap = getIntInput("Enter port container capacity: ", 1, Integer.MAX_VALUE);
            
            // Check for duplicate port ID using a simple loop (beginner friendly)
            for (Ports existingPort : ports) {
                if (existingPort.getPortId() == id) {
                    throw new IllegalArgumentException("Port with ID " + id + " already exists.");
                }
            }
            
            // Create a new port and add it to the list
            Ports port = new Ports(name, location, id, cap);
            ports.add(port);
            System.out.println("Port " + name + " registered successfully.");
        } catch (Exception e) {
            System.out.println("Error registering port: " + e.getMessage());
        }
    }

    @Override
    public void displayDetails() {
        try {
            if (ports.isEmpty()) {
                System.out.println("No ports registered.");
            } else {
                System.out.println("\n=== All Ports ===\n");
                // Loop through each port and display its details
                for (Ports port : ports) {
                    port.displayDetails();
                }
            }
            // If there are any ship time records, display them as well
            if (!timeRecords.isEmpty()) {
                System.out.println("\n=== Ship Time Records ===\n");
                for (PortTimeRecord record : timeRecords) {
                    System.out.println(record);
                }
            }
        } catch (Exception e) {
            System.out.println("Error displaying port details: " + e.getMessage());
        }
    }

    @Override
    public void manageContainers() {
        try {
            System.out.println("\n--- Manage Containers ---");
            // For now, container management simply calls the displayDetails() method
            displayDetails();
        } catch (Exception e) {
            System.out.println("Error managing containers: " + e.getMessage());
        }
    }

    // Retrieve a port by its ID using a simple for loop
    public Ports getPortById(int id) {
        try {
            for (Ports port : ports) {
                if (port.getPortId() == id) {
                    return port;
                }
            }
            throw new IllegalArgumentException("Port not found");
        } catch (Exception e) {
            System.out.println("Error retrieving port: " + e.getMessage());
            return null;
        }
    }

    // Pre-load some ports (optional)
    public static void initializePorts(ArrayList<Ports> ports) {
        try {
            ports.add(new Ports("Mumbai", "Mumbai", 1, 800));
            ports.add(new Ports("Chennai", "Chennai", 2, 400));
            ports.add(new Ports("Vizag", "Visakhapatnam", 3, 1000));
        } catch (Exception e) {
            System.out.println("Error initializing ports: " + e.getMessage());
        }
    }

    // Record the arrival of a ship at a port using scanner input
    public void recordShipArrival() {
        try {
            System.out.println("\n--- Record Ship Arrival ---");
            System.out.print("Enter ship name: ");
            String shipName = scanner.nextLine();
            System.out.print("Enter port ID: ");
            int portId = getIntInput("", 1, Integer.MAX_VALUE);
            // Get the port based on the provided port ID
            Ports port = getPortById(portId);
            if (port == null) {
                System.out.println("Port not found.");
                return;
            }
            // Get the current time for arrival
            LocalDateTime arrivalTime = LocalDateTime.now();
            // Create a new PortTimeRecord object using the ship name and arrival time
            PortTimeRecord record = new PortTimeRecord(shipName, arrivalTime);
            timeRecords.add(record);
            System.out.println("Recorded arrival of ship " + shipName +
                " at port " + port.getPortName() +
                " at " + record.getFormattedArrivalTime("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            System.out.println("Error recording ship arrival: " + e.getMessage());
        }
    }

    // Record the departure of a ship from a port using scanner input
    public void recordShipDeparture() {
        try {
            System.out.println("\n--- Record Ship Departure ---");
            System.out.print("Enter ship name: ");
            String shipName = scanner.nextLine();
            PortTimeRecord foundRecord = null;
            // Loop through time records to find the matching arrival record that has not been departed yet
            for (PortTimeRecord record : timeRecords) {
                if (record.getShipName().equalsIgnoreCase(shipName) &&
                    record.getFormattedDepartureTime("yyyy-MM-dd HH:mm:ss").equals("Not departed yet")) {
                    foundRecord = record;
                    break;
                }
            }
            if (foundRecord == null) {
                System.out.println("No active arrival record found for ship " + shipName);
                return;
            }
            // Get the current time for departure and update the record
            LocalDateTime departureTime = LocalDateTime.now();
            foundRecord.setDepartureTime(departureTime);
            System.out.println("Recorded departure of ship " + shipName +
                " at " + foundRecord.getFormattedDepartureTime("yyyy-MM-dd HH:mm:ss"));
            System.out.println("Port stay duration: " +
                foundRecord.getPortStayDuration().getSeconds() + " seconds.");
        } catch (Exception e) {
            System.out.println("Error recording ship departure: " + e.getMessage());
        }
    }

    // Overloaded method to record ship arrival using a Ships object and a Ports object
    public void recordShipArrival(Ships ship, Ports port) {
        LocalDateTime arrivalTime = LocalDateTime.now();
        PortTimeRecord record = new PortTimeRecord(ship.shipName, port.getPortName(), arrivalTime);
        timeRecords.add(record);
        System.out.println("Recorded arrival of ship " + ship.shipName +
            " at port " + port.getPortName() +
            " at " + record.getFormattedArrivalTime("yyyy-MM-dd HH:mm:ss"));
    }

    // Overloaded method to record ship departure using a Ships object
    public void recordShipDeparture(Ships ship) {
        for (PortTimeRecord record : timeRecords) {
            if (record.getShipName().equalsIgnoreCase(ship.shipName) &&
                record.getFormattedDepartureTime("yyyy-MM-dd HH:mm:ss").equals("Not departed yet")) {
                LocalDateTime departureTime = LocalDateTime.now();
                record.setDepartureTime(departureTime);
                System.out.println("Recorded departure of ship " + ship.shipName +
                    " from port " + record.getPortName() + " at " +
                    record.getFormattedDepartureTime("yyyy-MM-dd HH:mm:ss"));
                System.out.println("Port stay duration: " +
                    record.getPortStayDuration().getSeconds() + " seconds.");
                return;
            }
        }
        System.out.println("No active arrival record found for ship " + ship.shipName);
    }

    // Helper method to safely read an integer input within a given range
    private int getIntInput(String prompt, int min, int max) {
        int input = 0;
        while (true) {
            try {
                if (!prompt.isEmpty()) {
                    System.out.print(prompt);
                }
                input = scanner.nextInt();
                scanner.nextLine(); // clear newline from input
                if (input >= min && input <= max) {
                    break;
                }
                System.out.println("Please enter a value between " + min + " and " + max);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a valid number.");
                scanner.nextLine(); // clear buffer
            }
        }
        return input;
    }
}
