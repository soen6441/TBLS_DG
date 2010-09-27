import java.util.*;


public class Result {

//*********************************  fields **********************************************
private Vector<Vertex> CDS;
private Vector<Vector<Vertex>> prunedCDSs;
private int minDegreeBeforePruning;
private int maxDegreeBeforePruning;
private int minDegreeAfterPruning;
private int maxDegreeAfterPruning;
private double averageDegreeBeforePruning;
private double averageDegreeAfterPruning;
private int minNumOfNodesInHexagonBeforePruning;
private int maxNumOfNodesInHexagonBeforePruning;
private double averageNumOfNodesInHexagonBeforePruning;
private int minNumOfNodesInHexagonAfterPruning;
private int maxNumOfNodesInHexagonAfterPruning;
private double averageNumOfNodesInHexagonAfterPruning;
//***************************** constructors ************************************************
public Result(Vector<Vertex> CDS, Vector<Vector<Vertex>> prunedCDSs){
	this.CDS=CDS;
	this.prunedCDSs=prunedCDSs;
}

//********************************getters and setters  **************************************
public void setCDS(Vector<Vertex> cDS) {
	CDS = cDS;
}
public  Vector<Vertex> getCDS() {
	return CDS;
}
public  void setPrunedCDS(Vector<Vector<Vertex>> prunedCDSs) {
	this.prunedCDSs = prunedCDSs;
}
public  Vector<Vector<Vertex>> getPrunedCDSs() {
	return this.prunedCDSs;
}
public  void setMinDegreeBeforePruning(int minDegreeBeforePruning) {
	this.minDegreeBeforePruning = minDegreeBeforePruning;
}
public  int getMinDegreeBeforePruning() {
	return minDegreeBeforePruning;
}
public  void setMaxDegreeBeforePruning(int maxDegreeBeforePruning) {
	this.maxDegreeBeforePruning = maxDegreeBeforePruning;
}
public  int getMaxDegreeBeforePruning() {
	return maxDegreeBeforePruning;
}
public  void setMinDegreeAfterPruning(int minDegreeAfterPruning) {
	this.minDegreeAfterPruning = minDegreeAfterPruning;
}
public  int getMinDegreeAfterPruning() {
	return minDegreeAfterPruning;
}
public  void setMaxDegreeAfterPruning(int maxDegreeAfterPruning) {
	this.maxDegreeAfterPruning = maxDegreeAfterPruning;
}
public  int getMaxDegreeAfterPruning() {
	return maxDegreeAfterPruning;
}
public  void setAverageDegreeBeforePruning(double averageDegreeBeforePruning) {
	this.averageDegreeBeforePruning = averageDegreeBeforePruning;
}
public  double getAverageDegreeBeforePruning() {
	return averageDegreeBeforePruning;
}
public  void setAverageDegreeAfterPruning(double averageDegreeAfterPruning) {
	this.averageDegreeAfterPruning = averageDegreeAfterPruning;
}
public  double getAverageDegreeAfterPruning() {
	return averageDegreeAfterPruning;
}
public  void setMinNumOfNodesInHexagonBeforePruning(int minNumOfNodesInHexagon) {
	this.minNumOfNodesInHexagonBeforePruning = minNumOfNodesInHexagon;
}
public  int getMinNumOfNodesInHexagonBeforePruning() {
	return minNumOfNodesInHexagonBeforePruning;
}
public  void setMaxNumOfNodesInHexagonBeforePruning(int maxNumOfNodesInHexagon) {
	this.maxNumOfNodesInHexagonBeforePruning = maxNumOfNodesInHexagon;
}
public  int getMaxNumOfNodesInHexagonBeforePruning() {
	return maxNumOfNodesInHexagonBeforePruning;
}
public  void setAverageNumOfNodesInHexagonBeforePruning(double averageNumOfNodesInHexagon) {
	this.averageNumOfNodesInHexagonBeforePruning = averageNumOfNodesInHexagon;
}
public  double getAverageNumOfNodesInHexagonBeforePruning() {
	return averageNumOfNodesInHexagonBeforePruning;
}
public void setMinNumOfNodesInHexagonAfterPruning(
		int minNumOfNodesInHexagonAfterPruning) {
	this.minNumOfNodesInHexagonAfterPruning = minNumOfNodesInHexagonAfterPruning;
}

public int getMinNumOfNodesInHexagonAfterPruning() {
	return minNumOfNodesInHexagonAfterPruning;
}

public void setMaxNumOfNodesInHexagonAfterPruning(
		int maxNumOfNodesInHexagonAfterPruning) {
	this.maxNumOfNodesInHexagonAfterPruning = maxNumOfNodesInHexagonAfterPruning;
}

public int getMaxNumOfNodesInHexagonAfterPruning() {
	return maxNumOfNodesInHexagonAfterPruning;
}

public void setAverageNumOfNodesInHexagonAfterPruning(
		double averageNumOfNodesInHexagonAfterPruning) {
	this.averageNumOfNodesInHexagonAfterPruning = averageNumOfNodesInHexagonAfterPruning;
}

public double getAverageNumOfNodesInHexagonAfterPruning() {
	return averageNumOfNodesInHexagonAfterPruning;
}
//****************************************************************************************
/*public void calculateStatistics(Graph preliminaryCDS, Graph finalCDS )
{
	//*************** max/min and average degree of nodes before pruning********************
	int minDegree=1000000;
	int maxDegree=0;
	int sumOfDegrees=0;
	double averageDegreeBeforePruning=0;
	for (Vertex v:preliminaryCDS.getVertices())
	{
		if (v.getNeighbors().size()<minDegree)
			minDegree=v.getNeighbors().size();
		if (v.getNeighbors().size()>maxDegree)
			maxDegree=v.getNeighbors().size();
		sumOfDegrees+=v.getNeighbors().size();
		
	}
	averageDegreeBeforePruning= (double) sumOfDegrees/preliminaryCDS.getVertices().size();
	this.maxDegreeBeforePruning= maxDegree;
	this.minDegreeBeforePruning=minDegree;
	this.averageDegreeBeforePruning=averageDegreeBeforePruning;
	
	//*************** max/min and average degree of nodes after pruning********************		
	minDegree=1000000;
	maxDegree=0;
	sumOfDegrees=0;
	double averageDegreeAfterPruning=0;
	for (Vertex v:finalCDS.getVertices())
	{
		if (v.getNeighbors().size()<minDegree)
			minDegree=v.getNeighbors().size();
		if (v.getNeighbors().size()>maxDegree)
			maxDegree=v.getNeighbors().size();
		sumOfDegrees+=v.getNeighbors().size();
		
	}
	averageDegreeAfterPruning= (double) sumOfDegrees/finalCDS.getVertices().size();
	this.maxDegreeAfterPruning=maxDegree;
	this.minDegreeAfterPruning=minDegree;
	this.averageDegreeAfterPruning=averageDegreeAfterPruning;
	
	//*****************************calculate num of nodes in hexagons before pruning ***********************
	Vector<Vertex> CDSVertices=new Vector<Vertex>();
	for (Vertex v: preliminaryCDS.getVertices())
		CDSVertices.add(v);
	int minNumOfNodes=1000000;
	int maxNumOfNodes=0;
	int sumOfNodes=0;
	int numOfHexagons=0;
	while(!CDSVertices.isEmpty())
	{
		Vertex v=CDSVertices.firstElement();
		int count=1;
		for (Vertex u:v.getNeighbors())
		{
			if (u.getClassNum()==v.getClassNum())
			{
				count++;
				CDSVertices.remove(u);
			}
		}
		numOfHexagons++;
		CDSVertices.remove(v);
		if (count>maxNumOfNodes)
			maxNumOfNodes=count;
		if (count<minNumOfNodes)
			minNumOfNodes=count;
		sumOfNodes+=count;
		
	}
	this.maxNumOfNodesInHexagonBeforePruning=maxNumOfNodes;
	this.minNumOfNodesInHexagonBeforePruning=minNumOfNodes;
	this.averageNumOfNodesInHexagonBeforePruning=(double)sumOfNodes/numOfHexagons;
	
	//*****************************calculate num of nodes in hexagons after pruning ***********************
	CDSVertices=new Vector<Vertex>();
	CDSVertices.clear();
	for (Vertex v: finalCDS.getVertices())
		CDSVertices.add(v);
	minNumOfNodes=1000000;
	maxNumOfNodes=0;
	sumOfNodes=0;
	numOfHexagons=0;
	while(!CDSVertices.isEmpty())
	{
		Vertex v=CDSVertices.firstElement();
		int count=1;
		for (Vertex u:v.getNeighbors())
		{
			if (u.getClassNum()==v.getClassNum())
			{
				count++;
				CDSVertices.remove(u);
			}
		}
		numOfHexagons++;
		CDSVertices.remove(v);
		if (count>maxNumOfNodes)
			maxNumOfNodes=count;
		if (count<minNumOfNodes)
			minNumOfNodes=count;
		sumOfNodes+=count;
		
	}
	this.maxNumOfNodesInHexagonAfterPruning=maxNumOfNodes;
	this.minNumOfNodesInHexagonAfterPruning=minNumOfNodes;
	this.averageNumOfNodesInHexagonAfterPruning=(double)sumOfNodes/numOfHexagons;
	
	*//*******************************************************************************************************//*
	
}*/








}
