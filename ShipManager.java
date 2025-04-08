package Ships;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ShipManager {
    private static Scanner scanner = new Scanner(System.in);
    private ArrayList<Ships> ships;
    private ArrayList<Ports> ports;

    public ShipManager(ArrayList<Ships> ships, ArrayList<Ports> ports) {
        // Initialize the list of ships and ports
        this.ships = ships;
        this.setPorts(ports);
    }

    public void registerShip() {
        try {
            System.out.println("\n--- Register New Ship ---");
            String shipName = getStringInput("Enter ship name: ");
            int shipId = getIntInput("Enter ship ID: ", 1, Integer.MAX_VALUE);
            float capacity = getFloatInput("Enter ship capacity (tons): ", 1, Float.MAX_VALUE);
            
            for (Ships s : ships) {
                if (s.shipId == shipId) {
                    throw new IllegalArgumentException("Ship ID already exists");
                }
            }
            
            Ships newShip = new Ships(shipName, shipId, capacity);
            ships.add(newShip);
            System.out.println("Ship " + shipName + " registered successfully.");
        } catch (Exception e) {
            System.out.println("Error registering ship: " + e.getMessage());
        }
    }

    public void displayShipDetails() {
        if (ships.isEmpty()) {
            System.out.println("No ships registered.");
            return;
        }
        System.out.println("\n=== All Ships ===");
        for (int i = 0; i < ships.size(); i++) {
            Ships ship = ships.get(i);
            System.out.println((i + 1) + ". " + ship.shipName + " (ID: " + ship.shipId + ", Location: " + ship.currentLocation + ")");
            ship.displayDetails();
        }
    }

    public Ships getShipById(int id) {
        for (Ships s : ships) {
            if (s.shipId == id) {
                return s;
            }
        }
        throw new IllegalArgumentException("Ship not found");
    }
    
    public Ships selectShip() {
        if (ships.isEmpty()) {
            System.out.println("No ships available.");
            return null;
        }
        System.out.println("\nSelect a ship (enter the corresponding number):");
        for (int i = 0; i < ships.size(); i++) {
            Ships ship = ships.get(i);
            System.out.println((i + 1) + ". " + ship.shipName + " (ID: " + ship.shipId + ", Location: " + ship.currentLocation + ")");
        }
        int choice = getIntInput("Enter ship number: ", 1, ships.size());
        return ships.get(choice - 1);
    }

    public static void initializeShips(ArrayList<Ships> ships) {
        ships.add(new Ships("Titanic", 1, 100));
        ships.add(new Ships("Climac", 2, 300));
        ships.add(new Ships("Tile", 3, 500));
    }

    private int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Please enter a value between " + min + " and " + max);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine(); // clear the buffer
            }
        }
    }

    private float getFloatInput(String prompt, float min, float max) {
        while (true) {
            try {
                System.out.print(prompt);
                float input = scanner.nextFloat();
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Please enter a value between " + min + " and " + max);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number");
                scanner.nextLine(); // clear the buffer
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.next();
    }

	public ArrayList<Ports> getPorts() {
		return ports;
	}

	public void setPorts(ArrayList<Ports> ports) {
		this.ports = ports;
	}
}
