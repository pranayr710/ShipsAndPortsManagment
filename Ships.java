package Ships;

import java.util.ArrayList;
import container.*;

public class Ships {
    // Ship properties
    public String shipName;
    public int shipId;
    public float maxWeightCapacity;
    public float currentWeight = 0;
    public int currentCount = 0;
    public String status;
    private final String empty = "Empty";
    private final String availableToLoad = "AvailableToLoad";
    private final String full = "Full";
    public int basicContainer = 0;
    public int heavyContainers = 0;
    public int liquidContainers = 0;
    public int refrigeratedContainers = 0;
    public String currentLocation;
    
    // List to hold containers onboard
    public ArrayList<Containers> onboardContainers = new ArrayList<>();

    public Ships() {}

    public Ships(String name, int id, float maxCap) {
        this.shipName = name;
        this.shipId = id;
        this.maxWeightCapacity = maxCap;
        this.status = empty;
        this.currentLocation = "At Sea";
    }

    public void displayDetails() {
        System.out.println("\nShip Details:");
        System.out.println("Name: " + shipName);
        System.out.println("ID: " + shipId);
        System.out.println("Max Capacity: " + maxWeightCapacity + " tons");
        System.out.println("Current Weight: " + currentWeight + " tons");
        System.out.println("Container Count: " + currentCount);
        System.out.println("Status: " + status);
        System.out.println("Location: " + currentLocation);
        System.out.println("Basic Containers: " + basicContainer);
        System.out.println("Heavy Containers: " + heavyContainers);
        System.out.println("Liquid Containers: " + liquidContainers);
        System.out.println("Refrigerated Containers: " + refrigeratedContainers);
    }

    // ----------------------
    // LOAD METHODS
    // ----------------------

    public void loadBC(int count, ArrayList<container.BasicContainers> availableBasic) {
        if (count <= 0) 
            throw new IllegalArgumentException("Number must be positive");
        if (availableBasic.size() < count) 
            throw new IllegalArgumentException("Not enough Basic Containers available");
        
        float totalWeight = 0;
        for (int i = 0; i < count; i++) {
            totalWeight += availableBasic.get(i).getContainerWeight();
        }
        
        if (currentWeight + totalWeight > maxWeightCapacity) {
            throw new IllegalStateException("Exceeds ship's weight capacity");
        }
        
        for (int i = 0; i < count; i++) {
            // Remove the container from availableBasic and add it to onboardContainers
            container.BasicContainers container = availableBasic.remove(0);
            onboardContainers.add(container);
            currentWeight += container.getContainerWeight();
            currentCount++;
            basicContainer++;
        }
        
        status = (currentWeight >= maxWeightCapacity) ? full : availableToLoad;
        System.out.println(count + " Basic Container(s) loaded successfully on ship " + shipName);
    }

    public void loadHC(int count, ArrayList<container.HeavyContainers> availableHeavy) {
        if (count <= 0) 
            throw new IllegalArgumentException("Number must be positive");
        if (availableHeavy.size() < count) 
            throw new IllegalArgumentException("Not enough Heavy Containers available");
        
        float totalWeight = 0;
        for (int i = 0; i < count; i++) {
            totalWeight += availableHeavy.get(i).getContainerWeight();
        }
        
        if (currentWeight + totalWeight > maxWeightCapacity) {
            throw new IllegalStateException("Exceeds ship's weight capacity");
        }
        
        for (int i = 0; i < count; i++) {
            container.HeavyContainers container = availableHeavy.remove(0);
            onboardContainers.add(container);
            currentWeight += container.getContainerWeight();
            currentCount++;
            heavyContainers++;
        }
        
        status = (currentWeight >= maxWeightCapacity) ? full : availableToLoad;
        System.out.println(count + " Heavy Container(s) loaded successfully on ship " + shipName);
    }

    public void loadLC(int count, ArrayList<container.LiquidContainers> availableLiquid) {
        if (count <= 0) 
            throw new IllegalArgumentException("Number must be positive");
        if (availableLiquid.size() < count) 
            throw new IllegalArgumentException("Not enough Liquid Containers available");
        
        float totalWeight = 0;
        for (int i = 0; i < count; i++) {
            totalWeight += availableLiquid.get(i).getContainerWeight();
        }
        
        if (currentWeight + totalWeight > maxWeightCapacity) {
            throw new IllegalStateException("Exceeds ship's weight capacity");
        }
        
        for (int i = 0; i < count; i++) {
            container.LiquidContainers container = availableLiquid.remove(0);
            onboardContainers.add(container);
            currentWeight += container.getContainerWeight();
            currentCount++;
            liquidContainers++;
        }
        
        status = (currentWeight >= maxWeightCapacity) ? full : availableToLoad;
        System.out.println(count + " Liquid Container(s) loaded successfully on ship " + shipName);
    }

    public void loadRC(int count, ArrayList<container.RefridgeratedContainers> availableRefrig) {
        if (count <= 0) 
            throw new IllegalArgumentException("Number must be positive");
        if (availableRefrig.size() < count) 
            throw new IllegalArgumentException("Not enough Refrigerated Containers available");
        
        float totalWeight = 0;
        for (int i = 0; i < count; i++) {
            totalWeight += availableRefrig.get(i).getContainerWeight();
        }
        
        if (currentWeight + totalWeight > maxWeightCapacity) {
            throw new IllegalStateException("Exceeds ship's weight capacity");
        }
        
        for (int i = 0; i < count; i++) {
            container.RefridgeratedContainers container = availableRefrig.remove(0);
            onboardContainers.add(container);
            currentWeight += container.getContainerWeight();
            currentCount++;
            refrigeratedContainers++;
        }
        
        status = (currentWeight >= maxWeightCapacity) ? full : availableToLoad;
        System.out.println(count + " Refrigerated Container(s) loaded successfully on ship " + shipName);
    }

    // ----------------------
    // UNLOAD METHODS
    // ----------------------

    public void unloadBC(int count, ArrayList<container.BasicContainers> availableBasic, Ports port) {
        if (count <= 0) 
            throw new IllegalArgumentException("Number must be positive");
        if (basicContainer < count)
            throw new IllegalArgumentException("Not enough Basic Containers on ship");
        
        for (int i = 0; i < count; i++) {
            // Use a simple loop to find the first Basic Container on board
            container.BasicContainers container = null;
            for (Containers c : onboardContainers) {
                if (c instanceof container.BasicContainers) {
                    container = (container.BasicContainers) c;
                    break;
                }
            }
            if (container == null) {
                throw new IllegalStateException("No Basic Container found on ship");
            }
            // Remove the container from the ship and add it back to availableBasic
            onboardContainers.remove(container);
            availableBasic.add(container);
            container.initialLoc = port.getPortId();  // update container's location
            currentWeight -= container.containerWeight;
            currentCount--;
            basicContainer--;
            // Update the port's container counts
            port.currentContainerCount++;
            port.basicContainer++;
        }
        
        status = (currentCount == 0) ? empty : availableToLoad;
        System.out.println(count + " Basic Container(s) unloaded successfully at port " + port.getPortName());
    }

    public void unloadHC(int count, ArrayList<container.HeavyContainers> availableHeavy, Ports port) {
        if (count <= 0) 
            throw new IllegalArgumentException("Number must be positive");
        if (heavyContainers < count)
            throw new IllegalArgumentException("Not enough Heavy Containers on ship");
        
        for (int i = 0; i < count; i++) {
            // Find the first Heavy Container on board using a loop
            container.HeavyContainers container = null;
            for (Containers c : onboardContainers) {
                if (c instanceof container.HeavyContainers) {
                    container = (container.HeavyContainers) c;
                    break;
                }
            }
            if (container == null) {
                throw new IllegalStateException("No Heavy Container found on ship");
            }
            onboardContainers.remove(container);
            availableHeavy.add(container);
            container.initialLoc = port.getPortId();
            currentWeight -= container.containerWeight;
            currentCount--;
            heavyContainers--;
            port.currentContainerCount++;
            port.heavyContainers++;
        }
        
        status = (currentCount == 0) ? empty : availableToLoad;
        System.out.println(count + " Heavy Container(s) unloaded successfully at port " + port.getPortName());
    }

    public void unloadLC(int count, ArrayList<container.LiquidContainers> availableLiquid, Ports port) {
        if (count <= 0) 
            throw new IllegalArgumentException("Number must be positive");
        if (liquidContainers < count)
            throw new IllegalArgumentException("Not enough Liquid Containers on ship");
        
        for (int i = 0; i < count; i++) {
            // Find the first Liquid Container on board with a simple loop
            container.LiquidContainers container = null;
            for (Containers c : onboardContainers) {
                if (c instanceof container.LiquidContainers) {
                    container = (container.LiquidContainers) c;
                    break;
                }
            }	
            if (container == null) {
                throw new IllegalStateException("No Liquid Container found on ship");
            }
            onboardContainers.remove(container);
            availableLiquid.add(container);
            container.initialLoc = port.getPortId();
            currentWeight -= container.containerWeight;
            currentCount--;
            liquidContainers--;
            port.currentContainerCount++;
            port.liquidContainers++;
        }
        
        status = (currentCount == 0) ? empty : availableToLoad;
        System.out.println(count + " Liquid Container(s) unloaded successfully at port " + port.getPortName());
    }

    public void unloadRC(int count, ArrayList<container.RefridgeratedContainers> availableRefrig, Ports port) {
        if (count <= 0) 
            throw new IllegalArgumentException("Number must be positive");
        if (refrigeratedContainers < count)
            throw new IllegalArgumentException("Not enough Refrigerated Containers on ship");
        
        for (int i = 0; i < count; i++) {
            // Use a loop to find the first Refrigerated Container on board
            container.RefridgeratedContainers container = null;
            for (Containers c : onboardContainers) {
                if (c instanceof container.RefridgeratedContainers) {
                    container = (container.RefridgeratedContainers) c;
                    break;
                }
            }
            if (container == null) {
                throw new IllegalStateException("No Refrigerated Container found on ship");
            }
            onboardContainers.remove(container);
            availableRefrig.add(container);
            container.initialLoc = port.getPortId();
            currentWeight -= container.containerWeight;
            currentCount--;
            refrigeratedContainers--;
            port.currentContainerCount++;
            port.refrigeratedContainers++;
        }
        
        status = (currentCount == 0) ? empty : availableToLoad;
        System.out.println(count + " Refrigerated Container(s) unloaded successfully at port " + port.getPortName());
    }
}
