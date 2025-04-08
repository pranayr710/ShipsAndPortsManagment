package container;

public class HeavyContainers extends Containers{
	
	
	public HeavyContainers(String containerType, int containerId, float containerWeight, int initialLoc) {
	    this.containerType = containerType;  
	    this.containerId = containerId;
	    this.containerWeight = containerWeight;
	    this.initialLoc = initialLoc;
	}
	public float getContainerWeight() {
		return containerWeight;
	}
	
}