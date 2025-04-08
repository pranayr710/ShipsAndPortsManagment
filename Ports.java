package Ships;

import java.util.ArrayList;

public class Ports {
    private String portName;
    private String location;
    private int portId;
    private int portContainerCapacity;
    public int currentContainerCount = 0;
    public int dockedShip = 0;
    public int basicContainer = 0;
    public int heavyContainers = 0;
    public int liquidContainers = 0;
    public int refrigeratedContainers = 0;
    private ArrayList<String> dockedShipNames = new ArrayList<>();

    static int count=0;

    public Ports(String name, String loc, int id, int capacity) {
        this.portName = name;
        this.location = loc;
        this.portId = id;
        this.portContainerCapacity = capacity;
    }

    public String getPortName() {
        return portName;
    }
    
    public String getLocation() {
        return location;
    }
    
    public int getPortId() {
        return portId;
    }
    
    public int getPortContainerCapacity() {
        return portContainerCapacity;
    }
    
    public int getCurrentContainerCount() {
        return currentContainerCount;
    }
    
    public int getDockedShip() {
        return dockedShip;
    }
    
    public int getBasicContainer() {
        return basicContainer;
    }
    
    public int getHeavyContainers() {
        return heavyContainers;
    }
    
    public int getLiquidContainers() {
        return liquidContainers;
    }
    
    public int getRefrigeratedContainers() {
        return refrigeratedContainers;
    }
    
    public ArrayList<String> getDockedShipNames() {
        return dockedShipNames;
    }
    
    // Methods for managing docked ships
    public void addDockedShip(String shipName) {
        dockedShip++;
        dockedShipNames.add(shipName);
    }
    
    public void removeDockedShip(String shipName) {
        if(dockedShipNames.remove(shipName)) {
            dockedShip--;
        }
    }
    
    public void displayDetails() {
        System.out.println("\nPort Details:");
        System.out.println("Name: " + portName);
        System.out.println("ID: " + portId);
        System.out.println("Location: " + location);
        System.out.println("Capacity: " + portContainerCapacity);
        System.out.println("Current Containers: " + currentContainerCount);
        System.out.println("Docked Ships: " + dockedShip);
        System.out.println("Docked Ship Names: " + dockedShipNames);
        System.out.println("Basic Containers: " + basicContainer);
        System.out.println("Heavy Containers: " + heavyContainers);
        System.out.println("Liquid Containers: " + liquidContainers);
        System.out.println("Refrigerated Containers: " + refrigeratedContainers);
    }
}
