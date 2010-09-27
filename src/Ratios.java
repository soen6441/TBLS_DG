
public class Ratios {
	
	private double averageHopLengthOnGraph;
	private double averageHopLengthViaCDS;
	public Ratios()
	{
		
	}
	public Ratios(double AOG, double AVCDS)
	{
		this.setAverageHopLengthOnGraph(AOG);
		this.setAverageHopLengthViaCDS(AVCDS);
	}
	
	private void setAverageHopLengthViaCDS(double averageHopLengthViaCDS) {
		this.averageHopLengthViaCDS = averageHopLengthViaCDS;
	}
	public double getAverageHopLengthViaCDS() {
		return averageHopLengthViaCDS;
	}
	private void setAverageHopLengthOnGraph(double averageHopLengthOnGraph) {
		this.averageHopLengthOnGraph = averageHopLengthOnGraph;
	}
	public double getAverageHopLengthOnGraph() {
		return averageHopLengthOnGraph;
	}
	

}
