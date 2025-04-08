
package main;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

import container.BasicContainers;
import container.HeavyContainers;
import container.LiquidContainers;
import container.RefridgeratedContainers;
import Ships.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Ships> ships = new ArrayList<>();
    private static ArrayList<Ports> ports = new ArrayList<>();
    private static ArrayList<BasicContainers> basicContainers = new ArrayList<>();
    private static ArrayList<HeavyContainers> heavyContainers = new ArrayList<>();
    private static ArrayList<LiquidContainers> liquidContainers = new ArrayList<>();
    private static ArrayList<RefridgeratedContainers> refrigeratedContainers = new ArrayList<>();
    // Create a PortManager instance for port operations (including time tracking)
    private static PortManager portManager = new PortManager(ports);
    // Create a ShipManager instance for ship operations
    private static ShipManager shipManager = new ShipManager(ships, ports);

    public static void main(String[] args) {
        // Pre-load some ports using PortManager's initializePorts() method
        PortManager.initializePorts(ports);
        // Pre-load some ships using ShipManager's initializeShips() method
        ShipManager.initializeShips(ships);

        while (true) {
            System.out.println("\n===============MENU===============");
            System.out.println("1. Register a port");
            System.out.println("2. Register a ship");
            System.out.println("3. Display all registered ports");
            System.out.println("4. Display all registered ships");
            System.out.println("5. Register containers");
            System.out.println("6. Load containers onto a ship");
            System.out.println("7. Display all registered containers");
            System.out.println("8. Travel to a port");
            System.out.println("9. Unload containers from a ship");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                handleUserChoice(choice);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear buffer
            }
        }
    }

    private static void handleUserChoice(int choice) {
        switch (choice) {
            // For port operations, call the corresponding methods from PortManager.
            case 1 -> portManager.registerPort();
            // For ship operations, call the corresponding methods from ShipManager.
            case 2 -> shipManager.registerShip();
            case 3 -> portManager.displayDetails();
            case 4 -> shipManager.displayShipDetails();
            case 5 -> handleRegisterContainers();
            case 6 -> handleLoadShip();
            case 7 -> displayContainerDetails();
            case 8 -> handleTravelToPort();
            case 9 -> handleUnloadShip();
            case 10 -> {
                System.out.println("Exiting application...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void handleRegisterContainers() {
        System.out.println("\n--- Register Containers ---");

        while (true) {
            System.out.println("Available Container Types: Basic, Heavy, Liquid, Refrigerated, Exit");
            System.out.print("Enter container type: ");
            String type = scanner.next();

            if (type.equalsIgnoreCase("exit")) {
                System.out.println("Exiting container registration.");
                break;
            }

            if (!type.equalsIgnoreCase("Basic") && !type.equalsIgnoreCase("Heavy") && 
                !type.equalsIgnoreCase("Liquid") && !type.equalsIgnoreCase("Refrigerated")) {
                System.out.println("Invalid container type. Please enter a valid type.");
                continue;
            }

            if (ports.isEmpty()) {
                System.out.println("No ports registered. Please register a port first.");
                return;
            }

            System.out.println("Available Ports:");
            for (int i = 0; i < ports.size(); i++) {
                Ports port = ports.get(i);
                System.out.println((i + 1) + ". " + port.getPortName() + " (" + port.getLocation() + ") - Capacity: " +
                        (port.getPortContainerCapacity() - port.currentContainerCount) + " available");
            }

            System.out.print("Enter the number corresponding to the port: ");
            int portIndex = scanner.nextInt() - 1;
            if (portIndex < 0 || portIndex >= ports.size()) {
                System.out.println("Invalid port selection.");
                continue;
            }
            Ports selectedPort = ports.get(portIndex);

            System.out.print("Enter number of " + type + " containers to register: ");
            int count;
            // Read the container count as a String first to catch any non-numeric input
            try {
                String countInput = scanner.next();
                count = Integer.parseInt(countInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input for container count. Please enter a valid number.");
                continue;
            }

            // Check if the entered count is a positive number
            if (count <= 0) {
                System.out.println("Invalid container count. Please enter a positive integer.");
                continue;
            }

            if (selectedPort.currentContainerCount + count > selectedPort.getPortContainerCapacity()) {
                System.out.println("Error: Port cannot accommodate " + count + " more containers. Available space: " +
                        (selectedPort.getPortContainerCapacity() - selectedPort.currentContainerCount));
                continue;
            }

            for (int i = 0; i < count; i++) {
                int id = (basicContainers.size() + heavyContainers.size() + liquidContainers.size() + refrigeratedContainers.size() + 1);
                float weight;
                switch (type.toLowerCase()) {
                    case "basic" -> {
                        weight = 10.0f;
                        basicContainers.add(new BasicContainers("Basic", id, weight, selectedPort.getPortId()));
                        selectedPort.basicContainer++;
                    }
                    case "heavy" -> {
                        weight = 30.0f;
                        heavyContainers.add(new HeavyContainers("Heavy", id, weight, selectedPort.getPortId()));
                        selectedPort.heavyContainers++;
                    }
                    case "liquid" -> {
                        weight = 25.0f;
                        liquidContainers.add(new LiquidContainers("Liquid", id, weight, selectedPort.getPortId()));
                        selectedPort.liquidContainers++;
                    }
                    case "refrigerated" -> {
                        weight = 20.0f;
                        refrigeratedContainers.add(new RefridgeratedContainers("Refrigerated", id, weight, selectedPort.getPortId()));
                        selectedPort.refrigeratedContainers++;
                    }
                }
                selectedPort.currentContainerCount++;
            }
            System.out.println(count + " " + type + " container(s) registered successfully at " + selectedPort.getPortName());
        }
    }

    private static void handleTravelToPort() {
        System.out.println("\n--- Travel To Port ---");
        if (ships.isEmpty() || ports.isEmpty()) {
            System.out.println("Ensure that both ships and ports are registered first.");
            return;
        }

        System.out.println("Available Ships:");
        for (int i = 0; i < ships.size(); i++) {
            Ships ship = ships.get(i);
            System.out.println((i + 1) + ". " + ship.shipName + " (Current Location: " + ship.currentLocation + ")");
        }
        System.out.print("Select the ship by entering the corresponding number: ");
        int shipIndex = scanner.nextInt() - 1;
        if (shipIndex < 0 || shipIndex >= ships.size()) {
            System.out.println("Invalid ship selection.");
            return;
        }
        Ships selectedShip = ships.get(shipIndex);

        System.out.println("Available Ports:");
        for (int i = 0; i < ports.size(); i++) {
            Ports port = ports.get(i);
            System.out.println((i + 1) + ". " + port.getPortName() + " (" + port.getLocation() + ")");
        }
        System.out.print("Select the destination port by entering the corresponding number: ");
        int portIndex = scanner.nextInt() - 1;
        if (portIndex < 0 || portIndex >= ports.size()) {
            System.out.println("Invalid port selection.");
            return;
        }
        Ports destination = ports.get(portIndex);

        if (selectedShip.currentLocation.equalsIgnoreCase(destination.getLocation())) {
            System.out.println("Error: The ship is already at " + destination.getPortName() + ".");
            return;
        }

        if (!selectedShip.currentLocation.equalsIgnoreCase("At Sea")) {
            portManager.recordShipDeparture(selectedShip);
        }

        selectedShip.currentLocation = destination.getLocation();
        destination.dockedShip++;
        destination.addDockedShip(selectedShip.shipName);
        System.out.println("Ship " + selectedShip.shipName + " has traveled to " + destination.getPortName());

        portManager.recordShipArrival(selectedShip, destination);
    }

    private static void handleLoadShip() {
        System.out.println("\n--- Load Containers Onto Ship ---");
        if (ships.isEmpty()) {
            System.out.println("No ships registered. Please register a ship first.");
            return;
        }

        System.out.println("Available Ships:");
        for (int i = 0; i < ships.size(); i++) {
            Ships ship = ships.get(i);
            System.out.println((i + 1) + ". " + ship.shipName + " (Current Location: " + ship.currentLocation + ")");
        }
        System.out.print("Select the ship by entering the corresponding number: ");
        int shipIndex = scanner.nextInt() - 1;
        if (shipIndex < 0 || shipIndex >= ships.size()) {
            System.out.println("Invalid ship selection.");
            return;
        }
        Ships selectedShip = ships.get(shipIndex);

        if (selectedShip.currentLocation.equalsIgnoreCase("At Sea")) {
            System.out.println("The ship is at sea. Please select a port to dock:");
            for (int i = 0; i < ports.size(); i++) {
                Ports port = ports.get(i);
                System.out.println((i + 1) + ". " + port.getPortName() + " (" + port.getLocation() + ")");
            }
            System.out.print("Enter the number corresponding to the port: ");
            int portIndex = scanner.nextInt() - 1;
            if (portIndex < 0 || portIndex >= ports.size()) {
                System.out.println("Invalid port selection.");
                return;
            }
            Ports dockPort = ports.get(portIndex);
            selectedShip.currentLocation = dockPort.getLocation();
            dockPort.dockedShip++;
            dockPort.addDockedShip(selectedShip.shipName);
            System.out.println("Ship " + selectedShip.shipName + " docked at " + dockPort.getPortName() + ".");
        }

        System.out.print("Enter container type (Basic/Heavy/Liquid/Refrigerated): ");
        String type = scanner.next();
        System.out.print("Enter number of containers to load: ");
        int count = scanner.nextInt();

        try {
            switch (type.toLowerCase()) {
                case "basic" -> selectedShip.loadBC(count, basicContainers);
                case "heavy" -> selectedShip.loadHC(count, heavyContainers);
                case "liquid" -> selectedShip.loadLC(count, liquidContainers);
                case "refrigerated" -> selectedShip.loadRC(count, refrigeratedContainers);
                default -> System.out.println("Invalid container type.");
            }
        } catch (Exception e) {
            System.out.println("Error loading containers: " + e.getMessage());
        }
    }

    private static void handleUnloadShip() {
        System.out.println("\n--- Unload Containers From Ship ---");
        if (ships.isEmpty() || ports.isEmpty()) {
            System.out.println("Ensure that both ships and ports are registered first.");
            return;
        }

        System.out.println("Available Ships:");
        for (int i = 0; i < ships.size(); i++) {
            Ships ship = ships.get(i);
            System.out.println((i + 1) + ". " + ship.shipName + " (Current Location: " + ship.currentLocation + ")");
        }
        System.out.print("Select the ship by entering the corresponding number: ");
        int shipIndex = scanner.nextInt() - 1;
        if (shipIndex < 0 || shipIndex >= ships.size()) {
            System.out.println("Invalid ship selection.");
            return;
        }
        Ships selectedShip = ships.get(shipIndex);

        System.out.println("Available Destination Ports:");
        for (int i = 0; i < ports.size(); i++) {
            Ports port = ports.get(i);
            System.out.println((i + 1) + ". " + port.getPortName() + " (" + port.getLocation() + ")");
        }
        System.out.print("Select the destination port by entering the corresponding number: ");
        int portIndex = scanner.nextInt() - 1;
        if (portIndex < 0 || portIndex >= ports.size()) {
            System.out.println("Invalid port selection.");
            return;
        }
        Ports destination = ports.get(portIndex);

        System.out.print("Enter number of Basic containers to unload: ");
        int basicCount = scanner.nextInt();
        System.out.print("Enter number of Heavy containers to unload: ");
        int heavyCount = scanner.nextInt();
        System.out.print("Enter number of Liquid containers to unload: ");
        int liquidCount = scanner.nextInt();
        System.out.print("Enter number of Refrigerated containers to unload: ");
        int refrigCount = scanner.nextInt();

        try {
            if (basicCount > 0) {
                selectedShip.unloadBC(basicCount, basicContainers, destination);
            }
            if (heavyCount > 0) {
                selectedShip.unloadHC(heavyCount, heavyContainers, destination);
            }
            if (liquidCount > 0) {
                selectedShip.unloadLC(liquidCount, liquidContainers, destination);
            }
            if (refrigCount > 0) {
                selectedShip.unloadRC(refrigCount, refrigeratedContainers, destination);
            }
        } catch (Exception e) {
            System.out.println("Error unloading containers: " + e.getMessage());
        }
    }

    private static void displayContainerDetails() {
        System.out.println("\n=== Registered Containers ===");
        System.out.println("Basic Containers: " + basicContainers.size());
        System.out.println("Heavy Containers: " + heavyContainers.size());
        System.out.println("Liquid Containers: " + liquidContainers.size());
        System.out.println("Refrigerated Containers: " + refrigeratedContainers.size());
    }
}
