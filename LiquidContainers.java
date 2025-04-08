package container;


public class LiquidContainers extends Containers {
	
	LiquidContainers(){
		
	}
	public LiquidContainers(String containerType, int containerId, float containerWeight, int initialLoc) {
	    this.containerType = containerType;  
	    this.containerId = containerId;
	    this.containerWeight = containerWeight;
	    this.initialLoc = initialLoc;
	}
	public float getContainerWeight() {
		return containerWeight;
	}

}