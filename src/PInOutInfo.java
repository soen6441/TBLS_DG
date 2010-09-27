import java.util.*;
public class PInOutInfo {
	
	private Vector<Vector<Vertex>> CDASGroup;
	private Vector<Vector<Vertex>> initiators;
	private Vector<Integer> numOfRounds;
	
	public PInOutInfo(){
		CDASGroup = new Vector<Vector<Vertex>>();
		initiators = new Vector<Vector<Vertex>>();
		numOfRounds = new Vector<Integer>();
	}
	
	public Vector<Vector<Vertex>> getCDASGroup() {
		return CDASGroup;
	}
	public void setCDASGroup(Vector<Vector<Vertex>> cDASGroup) {
		CDASGroup = cDASGroup;
	}
	public Vector<Vector<Vertex>> getInitiators() {
		return initiators;
	}
	public void setInitiators(Vector<Vector<Vertex>> initiators) {
		this.initiators = initiators;
	}
	public Vector<Integer> getNumOfRounds() {
		return numOfRounds;
	}
	public void setNumOfRounds(Vector<Integer> numOfRounds) {
		this.numOfRounds = numOfRounds;
	}
	

}
