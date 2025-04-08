package container;


public abstract class Containers {
	
	public int containerId;
	public String containerType;
	public float containerWeight;
	public int initialLoc;
	

}




// AS my project uses only specific types of containers (like refrigerated ones or liquid ones)
//and never just used a plain "container," then keeping my Containers class as abstract is a good idea.
// as it  prevents someone from making a generic(common) container that doesn't fit into your more specific categories.

//If needed to create general containers without any specific features,
//then no need to make it abstract. it could be  regular class that can be instantiated.

// it is basically access restriction(prevents the class from being instantiated on its own, which means you can only use it through its subclasses)
