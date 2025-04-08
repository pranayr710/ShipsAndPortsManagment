package container;


public class RefridgeratedContainers extends Containers{
	
	RefridgeratedContainers(){
		
	}
	public RefridgeratedContainers(String containerType, int containerId, float containerWeight, int initialLoc) {
	    this.containerType = containerType;  
	    this.containerId = containerId;
	    this.containerWeight = containerWeight;
	    this.initialLoc = initialLoc;
	}
	public float getContainerWeight() {
		return containerWeight;
	}

}