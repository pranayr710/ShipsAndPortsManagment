package Ships;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import container.BasicContainers;
import container.HeavyContainers;
import container.LiquidContainers;
import container.RefridgeratedContainers;

public class ContainerManager {
    private Scanner scanner;
    private ArrayList<BasicContainers> basicContainers;
    private ArrayList<HeavyContainers> heavyContainers;
    private ArrayList<LiquidContainers> liquidContainers;
    private ArrayList<RefridgeratedContainers> refrigeratedContainers;
    private ArrayList<Ports> ports;

    public ContainerManager(ArrayList<BasicContainers> basicContainers, 
                            ArrayList<HeavyContainers> heavyContainers, 
                            ArrayList<LiquidContainers> liquidContainers, 
                            ArrayList<RefridgeratedContainers> refrigeratedContainers,
                            ArrayList<Ports> ports) {
        this.scanner = new Scanner(System.in);
        this.basicContainers = basicContainers;
        this.heavyContainers = heavyContainers;
        this.liquidContainers = liquidContainers;
        this.refrigeratedContainers = refrigeratedContainers;
        this.ports = ports;
    }

// user interface to register containers at specified ports.    
    
    public void registerContainers() {
        try {
            System.out.println("\n--- Register Containers ---");
            while (true) {
                System.out.println("Available Container Types: Basic, Heavy, Liquid, Refrigerated, Exit");
                System.out.print("Enter container type: ");
                String type = scanner.nextLine().trim();
                if (type.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting container registration.");
                    break;
                }
                if (ports.isEmpty()) {
                    System.out.println("No ports registered. Please register a port first.");
                    continue;
                }
                System.out.println("Available Ports:");
                for (int i = 0; i < ports.size(); i++) {
                    Ports port = ports.get(i);
                    System.out.println((i + 1) + ". " + port.getPortName() + " (" + port.getLocation() + ") - Capacity: " +
                            (port.getPortContainerCapacity() - port.getCurrentContainerCount()) + " available");
                }
                System.out.print("Enter the number corresponding to the port: ");
                int portIndex = getIntInput("", 1, ports.size()) - 1;
                Ports selectedPort = ports.get(portIndex);
                System.out.print("Enter number of " + type + " containers to register: ");
                int count = getIntInput("", 1, Integer.MAX_VALUE);
                if (selectedPort.getCurrentContainerCount() + count > selectedPort.getPortContainerCapacity()) {
                    System.out.println("Error: Port cannot accommodate " + count + " more containers. Available space: " +
                            (selectedPort.getPortContainerCapacity() - selectedPort.getCurrentContainerCount()));
                    continue;
                }
                for (int i = 0; i < count; i++) {
                    int id = (basicContainers.size() + heavyContainers.size() + liquidContainers.size() + refrigeratedContainers.size() + 1);
                    float weight = 0;
                    switch (type.toLowerCase()) {
                        case "basic":
                            weight = 10.0f;
                            basicContainers.add(new BasicContainers("Basic", id, weight, selectedPort.getPortId()));
                            selectedPort.basicContainer++;
                            break;
                        case "heavy":
                            weight = 30.0f;
                            heavyContainers.add(new HeavyContainers("Heavy", id, weight, selectedPort.getPortId()));
                            selectedPort.heavyContainers++;
                            break;
                        case "liquid":
                            weight = 25.0f;
                            liquidContainers.add(new LiquidContainers("Liquid", id, weight, selectedPort.getPortId()));
                            selectedPort.liquidContainers++;
                            break;
                        case "refrigerated":
                            weight = 20.0f;
                            refrigeratedContainers.add(new RefridgeratedContainers("Refrigerated", id, weight, selectedPort.getPortId()));
                            selectedPort.refrigeratedContainers++;
                            break;
                        default:
                            System.out.println("Invalid container type.");
                            continue;
                    }
                    selectedPort.currentContainerCount++;
                }
                System.out.println(count + " " + type + " container(s) registered successfully at " + selectedPort.getPortName());
            }
        } catch (Exception e) {
            System.out.println("Error in container registration: " + e.getMessage());
        }
    }

    public void displayAllContainers() {
        try {
            System.out.println("\n=== All Registered Containers ===");
            System.out.println("Basic Containers: " + basicContainers.size());
            System.out.println("Heavy Containers: " + heavyContainers.size());
            System.out.println("Liquid Containers: " + liquidContainers.size());
            System.out.println("Refrigerated Containers: " + refrigeratedContainers.size());
        } catch (Exception e) {
            System.out.println("Error displaying container details: " + e.getMessage());
        }
    }

    public void initializeContainers() {
        try {
            // Example initialization (if needed)
            basicContainers.add(new BasicContainers("Basic", 1, 10.0f, 1));
            heavyContainers.add(new HeavyContainers("Heavy", 1, 30.0f, 1));
            liquidContainers.add(new LiquidContainers("Liquid", 1, 25.0f, 1));
            refrigeratedContainers.add(new RefridgeratedContainers("Refrigerated", 1, 20.0f, 1));
        } catch (Exception e) {
            System.out.println("Error initializing containers: " + e.getMessage());
        }
    }

    private int getIntInput(String prompt, int min, int max) {
        int input = 0;
        while (true) {
            try {
                if (!prompt.isEmpty()) System.out.print(prompt);
                input = scanner.nextInt();
                scanner.nextLine(); // clear newline
                if (input >= min && input <= max) {
                    break;
                }
                System.out.println("Please enter a value between " + min + " and " + max);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please enter a valid number.");
                scanner.nextLine();
            }
        }
        return input;
    }

//    private String getStringInput(String prompt) {
//        try {
//            System.out.print(prompt);
//            return scanner.nextLine();
//        } catch (Exception e) {
//            System.out.println("Error reading input: " + e.getMessage());
//            return "";
//        }
//    }
}
