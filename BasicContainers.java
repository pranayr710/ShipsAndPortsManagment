package container;

public class BasicContainers extends Containers {
	
	
	public BasicContainers(String containerType, int containerId, float containerWeight, int initialLoc) {
	    this.containerType = containerType;  
	    this.containerId = containerId;
	    this.containerWeight = containerWeight;
	    this.initialLoc = initialLoc;
	}
	public float getContainerWeight() {
		return containerWeight;
	}

}