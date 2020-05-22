package cs415;

public class Node {
	/* For part 1; operations can only be done on boolean and integers; I just made them integers to be easier for me */
	private boolean isBool;
	private int value;
	private boolean instantiated;
	
	public Node() {	
	}
	public Node(boolean bool, int integer) {
		isBool = bool;
		value = integer;
		instantiated = true;
	}
	public Node(boolean bool, boolean isSet) {
		isBool = bool;
		instantiated = isSet;
	}
	
	public boolean isBool() {
		return isBool;
	}
	public void setBool(boolean isBool) {
		this.isBool = isBool;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public boolean isInstantiated() {
		return instantiated;
	}
	public void setInstantiated(boolean instantiated) {
		this.instantiated = instantiated;
	}
}
