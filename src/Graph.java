import java.util.*;


public class Graph {
 
 private int NumofNodes;
 private int minTransmissionRange;
 private int maxTransmissionRange;
 private double width;
 private double length;
 private Vector<Vertex> vertices;
 private TreeSet<Edge> edges;
  
 public Graph(int n){
	 this.NumofNodes=n;
	 this.vertices = new Vector<Vertex>();
	 this.edges = new TreeSet<Edge>();
	  
 }
 
//***************************** Getters and Setters ***************************************

 public void addVertex(Vertex v){
	 this.vertices.add(v);
 }
 
 public Vector<Vertex> getVertices(){
	 return this.vertices;
 }
 
 public TreeSet<Edge> getEdges(){
	 return this.edges;
 }
 
 public int getMinTransmissionRange ()
 {
	 return this.minTransmissionRange;
 }
 private void setMinTransmissionRange(int minRange)
 {
	 this.minTransmissionRange = minRange;
 }
 public int getMaxTransmissionRange ()
 {
	 return this.maxTransmissionRange;
 }
 private void setMaxTransmissionRange(int maxRange)
 {
	 this.maxTransmissionRange = maxRange;
 }
 public int getNumberOFNodes()
 {
	 return this.NumofNodes;
 }
 private void setNumberOfNodes(int numOfNodes)
 {
	 this.NumofNodes = numOfNodes;
 }
 public double getLength()
 {
	 return this.length;
 }
 private void setLength(double length)
 {
	 this.length = length;
 }
 public double getWidth()
 {
	 return this.width;
 }
private void setWidth(double width)
{
	this.width = width;
}
 
 //***************************************************************************************
 //***************************** Constructors ********************************************
 
 /*@param
  * n: number of nodes/vertices
  * range: transmission range of a node
  * w: width of the grid
  * l: length of the grid
  */
  
public Graph(int n, int minRange, int maxRange, double w, double l){
	 
	 Random randomGenerator0 = new Random();
	 Random randomGenerator1 = new Random();
	 Random randomGenerator2 = new Random();
	
	 	 
	 this.NumofNodes=n;
	 this.minTransmissionRange = minRange;
	 this.maxTransmissionRange = maxRange;
	 this.vertices= new Vector<Vertex>();
	 this.edges= new TreeSet<Edge>();
	 this.width = w;
	 this.length = l;
	      
	 

	 for (int i=0; i<this.NumofNodes ; i++)
	 {
		 double randomInt0 = randomGenerator0.nextDouble()*(maxRange - minRange)+ minRange;
		 double randomInt1 = length *(randomGenerator1.nextDouble());
		 randomInt1=randomInt1-length/2;
		 double randomInt2 = width* (randomGenerator2.nextDouble());
		 randomInt2=randomInt2-width/2;
		
		 this.vertices.add(new Vertex(i,randomInt0, randomInt1,randomInt2));
	 }
	 
	 ClassNumber cl= new ClassNumber();
	 for (Vertex v: this.getVertices())
		 v.setClassNum(cl.calculateClassNumInDG(v.getX(), v.getY(), minRange, maxRange));
	 
	 for (int i=0; i<this.vertices.size(); i++)
		 for (int j=0; j!=i && j<this.vertices.size(); j++)
		 {
			 if (this.vertices.elementAt(i).EuclideanDistance(this.vertices.elementAt(j))<= this.vertices.elementAt(i).getTransmissionRange())
			 {
			
				 this.edges.add(new Edge(this.vertices.elementAt(i),this.vertices.elementAt(j)));
				 this.vertices.elementAt(i).addOutgoingNeighbor(this.vertices.elementAt(j));
				 this.vertices.elementAt(j).addIncommingNeighbor(this.vertices.elementAt(i));
				 
			 }
			 if (this.vertices.elementAt(i).EuclideanDistance(this.vertices.elementAt(j))<= this.vertices.elementAt(j).getTransmissionRange())
			 {
				 this.edges.add(new Edge(this.vertices.elementAt(j),this.vertices.elementAt(i)));
				 this.vertices.elementAt(j).addOutgoingNeighbor(this.vertices.elementAt(i));
				 this.vertices.elementAt(i).addIncommingNeighbor(this.getVertices().elementAt(j));
				 
			 }
		    
	     }
	 //set the incoming/ingress (N_d) and outgoing/egress (N_a) neighbor list of all the nodes
	 for (Vertex v:this.getVertices())
		 for (Edge e:this.getEdges())
		 {
			 if (v.getID() == e.getVertexA().getID())
				 v.addToN_a(e.getVertexB());
			 if (v.getID() == e.getVertexB().getID())
				 v.addToN_d(e.getVertexA());
		 }
	//Set the neighbors of nodes with Bidirectional links (the intersection of N_a and N_d)
	 for (Vertex v:this.getVertices())
		 for (Vertex u:v.getN_a())
			 for (Vertex x:v.getN_d())
				 if (u.getID() == x.getID())
				 {
					 v.addToN_bi(u);
					 break;
				 }
	 //calculate the neighbor set of every node as the union of its N_a and N_d
	 for (Vertex v:this.getVertices())
	 {
		 for (Vertex u:v.getN_a())
			 v.addToN_a_U_N_d(u);
		 for (Vertex u:v.getN_d())
			 if (!v.getN_a_U_N_d().contains(u))
				 v.addToN_a_U_N_d(u);
	 }
 }

public Graph(Vector<Vertex> verticesList, int max_range, int min_range){
	 this.maxTransmissionRange = max_range;
	 this.minTransmissionRange = min_range;
	 this.NumofNodes = verticesList.size();
	 this.vertices= new Vector<Vertex>();
	 this.edges= new TreeSet<Edge>();
	 ClassNumber cl= new ClassNumber();
	 for (Vertex v:verticesList)
	 this.vertices.add(new Vertex(v.getID(), v.getTransmissionRange(), v.getX(), v.getY(),cl.calculateClassNumInDG(v.getX(), v.getY(), this.getMinTransmissionRange(), this.getMaxTransmissionRange())));
	 
	 
	 for (int i=0; i<this.vertices.size(); i++)
		 for (int j=0; j!=i && j<this.vertices.size(); j++)
		 {
			 if (this.vertices.elementAt(i).EuclideanDistance(this.vertices.elementAt(j))<= this.vertices.elementAt(i).getTransmissionRange())
			 {
			
				 this.edges.add(new Edge(this.vertices.elementAt(i),this.vertices.elementAt(j)));
				 this.vertices.elementAt(i).addOutgoingNeighbor(this.vertices.elementAt(j));
				 this.vertices.elementAt(j).addIncommingNeighbor(this.vertices.elementAt(i));
				 
			 }
			 if (this.vertices.elementAt(i).EuclideanDistance(this.vertices.elementAt(j))<= this.vertices.elementAt(j).getTransmissionRange())
			 {
				 this.edges.add(new Edge(this.vertices.elementAt(j),this.vertices.elementAt(i)));
				 this.vertices.elementAt(j).addOutgoingNeighbor(this.vertices.elementAt(i));
				 this.vertices.elementAt(i).addIncommingNeighbor(this.getVertices().elementAt(j));
				 
			 }
		    
	     }
	//set the incoming/ingress (N_d) and outgoing/egress (N_a) neighbor list of all the nodes
	 for (Vertex v:this.getVertices())
		 for (Edge e:this.getEdges())
		 {
			 if (v.getID() == e.getVertexA().getID())
				 v.addToN_a(e.getVertexB());
			 if (v.getID() == e.getVertexB().getID())
				 v.addToN_d(e.getVertexA());
		 }
	//Set the neighbors of nodes with Bidirectional links (the intersection of N_a and N_d)
	 for (Vertex v:this.getVertices())
		 for (Vertex u:v.getN_a())
			 for (Vertex x:v.getN_d())
				 if (u.getID() == x.getID())
				 {
					 v.addToN_bi(u);
					 break;
				 }
	 //calculate the neighbor set of every node as the union of its N_a and N_d
	 for (Vertex v:this.getVertices())
	 {
		 for (Vertex u:v.getN_a())
			 v.addToN_a_U_N_d(u);
		 for (Vertex u:v.getN_d())
			 if (!v.getN_a_U_N_d().contains(u))
				 v.addToN_a_U_N_d(u);
	 }
	
}
//**************************** End of Constructors ****************************************
//*****************************************************************************************
//************************* Strong connectivity Test for Unidirectional graphs ************
/*public Vertex isStronglyConnected(){
	
	//run the DFS for the original graph
	for (Vertex v:this.getVertices())
		v.clearDFSVisited();
	
	Vertex first = new Vertex();
	first = this.getVertices().firstElement();
	this.DFS(first);
	for (Vertex v:this.getVertices())
		if (!v.isDFSVisited())
			return v;
	//calculate the transpose of the graph
	Graph g = new Graph(this.getNumberOFNodes());
	g = this.transposeGraph();
	//run the DFS for the transpose graph
	for (Vertex v:g.getVertices())
		v.clearDFSVisited();
	//selecting the very same vertex as in the original graph
	for (Vertex v:g.getVertices())
		if (v.getID() == first.getID())
			first = v;
	
	g.DFS(first);
	for (Vertex v:g.getVertices())
		if (!v.isDFSVisited())
			return v;
	
	
	return new Vertex (-1,0,0,0);
	
}*/
public Vertex isStronglyConnected(){
	
	//run the DFS for the original graph
	for (Vertex v:this.getVertices())
		v.clearDFSVisited();
	
	Vertex first = new Vertex();
	first = this.getVertices().firstElement();
	this.DFS(first);
	for (Vertex v:this.getVertices())
		if (!v.isDFSVisited())
			return v;
	
	//run the reverse DFS for the original graph
	for (Vertex v:this.getVertices())
		v.clearDFSVisited();
	
	first = new Vertex();
	first = this.getVertices().firstElement();
	this.reverseDFS(first);
	for (Vertex v:this.getVertices())
		if (!v.isDFSVisited())
			return v;
	
	
	return new Vertex (-1,0,0,0);
	
}

public void DFS(Vertex first){
	 
	 first.markDFSVisited();
	 for (Vertex next:first.getOutgoingNeighbors())
	 {
		if (!next.isDFSVisited())
			 this.DFS(next);
			 
	 }
}

public void reverseDFS(Vertex first){
	 
	 first.markDFSVisited();
	 for (Vertex next:first.getIncommingNeighbors())
	 {
		if (!next.isDFSVisited())
			 this.DFS(next);
			 
	 }
}

//*****************************************************************************************
//************************** Calculate the Transpose of G *********************************
public Graph transposeGraph()
{
	ClassNumber cl = new ClassNumber();
	Graph g = new Graph(this.getNumberOFNodes());
	g.setMinTransmissionRange(this.getMinTransmissionRange());
	g.setMaxTransmissionRange(this.getMaxTransmissionRange());
	g.setLength(this.getLength());
	g.setWidth(this.getWidth());
	//the edge set of the transpose graph is the edge set of the original graph 
	//with all the edges being reversed
	for (Edge e:this.getEdges())
		g.getEdges().add(e.reverseEdge());
	
	//copy the vertices from the original graph
	for (Vertex v:this.getVertices())
	{
		g.getVertices().add(new Vertex(v.getID(), v.getTransmissionRange(), v.getX(), v.getY(),cl.calculateClassNumInDG(v.getX(),v.getY(), this.minTransmissionRange, this.maxTransmissionRange)));
	}
	//However, the neighbor list of all the vertices should be recalculated with regard to 
	//the new edge set of the transpose graph
	for (Vertex v:g.getVertices())
	{
		for (Edge e:g.getEdges())
			if (v.getID() == e.getVertexA().getID())
			{
				int id = e.getVertexB().getID();
				for (Vertex w:g.getVertices())
					if (w.getID() == id){
						v.addOutgoingNeighbor(w);
						w.addIncommingNeighbor(v);
					}
			}
	}
	//N_a and N_d are also to be recalculated with regard to the new edge set
	for (Vertex v:g.getVertices())
		 
		for (Edge e:g.getEdges())
		 {
			 if (v.getID() == e.getVertexA().getID())
			 {
				 int id = e.getVertexB().getID();
					for (Vertex w:g.getVertices())
						if (w.getID() == id)
							v.addToN_a(w);
			 }				 
			 else if (v.getID() == e.getVertexB().getID())
			 {
				 int id = e.getVertexA().getID();
					for (Vertex w:g.getVertices())
						if (w.getID() == id)
							v.addToN_d(w);
			 }
				
		 }
	 
	 return g;
}




//******************************************************************************************
//********************* testing if two sets of vertices DT and AT form a dominating **********
//****************************** and absorbent set of a graph ******************************
public Vertex isDominatingAndAbsorbent(Vector<Vertex>  DAS)
{
	boolean absorbentExists;
	boolean dominatorExists;
	Vector<Vertex> complementSet = new Vector<Vertex>();
	
	for (Vertex v:DAS)
		v.markAsDASNode();
	//fill the dominatorsComplement and AbsorbentsComplement sets
	for (Vertex v:this.getVertices())
	{
		if (!v.isDominatorOrAbsorbent())
			complementSet.add(v);
			
	}
	
	//All the nodes that are not in the dominating set must be dominated by at least one node
	for (Vertex v:complementSet)
	{
		dominatorExists = false;
		absorbentExists = false;
		for (Vertex u:v.getIncommingNeighbors())
			if (u.isDominatorOrAbsorbent())
			{
				dominatorExists = true;
				break;
			}
		for (Vertex u:v.getOutgoingNeighbors())
			if (u.isDominatorOrAbsorbent())
			{
				absorbentExists = true;
				break;
			}
		if (!dominatorExists)
		{
			System.out.println("Node "+v.getID()+" is not dominated!");
			return (v);
		}
		if (!absorbentExists)
		{
			System.out.println("Node "+v.getID()+" is not absorbed!");
			return (v);
		}
	}
	
	
	return new Vertex (-1,0,0,0);
}
//******************************************************************************************
 //outgoing kHopSubgraph
 public Vector<Vertex> kHopSubgraph(Vertex v,int k){
	 Vector<Vertex> vertexSet= new Vector<Vertex>();
	 Vector<Vertex> currentLevel= new Vector<Vertex>();
	 Vector<Vertex> nextLevel= new Vector<Vertex>();
	 currentLevel.add(v);
	 vertexSet.add(v);
	 v.markVisited();
	 for(int i=0; i<k; i++)
	 {
		for (int j=0; j!=currentLevel.size(); j++){
			for (int l=0; l!=currentLevel.elementAt(j).getOutgoingNeighbors().size();l++)
			{
				if (!currentLevel.elementAt(j).getOutgoingNeighbors().elementAt(l).isVisited())
				{
					nextLevel.add(currentLevel.elementAt(j).getOutgoingNeighbors().elementAt(l));
					vertexSet.add(currentLevel.elementAt(j).getOutgoingNeighbors().elementAt(l));
					currentLevel.elementAt(j).getOutgoingNeighbors().elementAt(l).markVisited();
				}
			}
		}
		currentLevel.clear();
		currentLevel.addAll(nextLevel);
		nextLevel.clear();
	 }
	 for (int i=0; i!=vertexSet.size(); i++)
		 vertexSet.elementAt(i).unmarkVisited();
	 
	 
	// TreeSet<Edge> subgraph= new TreeSet<Edge>();
	// subgraph.add(this);
	 return vertexSet;
	 
 }
 
 public boolean localSpanner(Edge e, int k)
	{
		Graph g= this.transposeGraph();
	 	Kruskal kr = new Kruskal();
		if (e.isInList(kr.spanner(this.getEdges(this.kHopSubgraph(e
				.getVertexA(), k))))
				&& new Edge(e.getVertexB(),e.getVertexA()).isInList(kr.spanner(g.getEdges(this.kHopSubgraph(e
						.getVertexB(), k)))))
			return true;
		else
			return false;
	}
 public void addEdge(Edge e){
	 this.edges.add(e);
 }
 
 //gets a vector containing all the vertices and returns a tree containing all the edges
 //For each vertex we create an edge between that edge and each of its neighbors and
 //add it to the list of edges (edgeList)
 public TreeSet<Edge> getEdges(Vector<Vertex> vlist){
	 TreeSet<Edge> edgeList= new TreeSet<Edge>();
	 for (int i=0; i!=vlist.size(); i++)
		 for (int j=0; j!=vlist.size(); j++) 
			 if (vlist.elementAt(j).isInList(vlist.elementAt(i).outgoingNeighborList))
				 edgeList.add(new Edge(vlist.elementAt(i),vlist.elementAt(j)));
	 return edgeList;
 }
 

 public Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2)
 {
     Set<Integer> s=new HashSet<Integer>();
     for (Iterator<Integer> it=set1.iterator(); it.hasNext();)
     {
         int i= it.next();
         if (set2.contains(i))
             s.add(i);
     }
     return s;
 }
 public Set<Vertex> intersection(Vector<Vertex> set1, Set<Vertex> set2)
 {
     Set<Vertex> s=new HashSet<Vertex>();
     for (Vertex v:set1)
     {
        for (Vertex u:set2)
           if (u.getID()==v.getID())
        		 {
        		 	s.add(v);
        		 	break;
        		 }
     }
     return s;
 }
 
 
 //******************************************************************************************
 //used in G_CMA
public Vector<Vertex> mergeList(Vector<Vertex> list1,Vector<Vertex> list2)
{
	for (Vertex v:list1)
		list2.add(v);
	return list2;
}

public Vector<Vector<Vertex>> getStronglyConnectedComponents(Vector<Vertex> vertexSet)
{
	Vector<Vector<Vertex>> components = new Vector<Vector<Vertex>>();
	
	return components;
}






//********************************************************************************************
public Vector<Vector<Vertex>> getConnectedComponents(Vector<Vertex> vertexSet)
{
	 Vector<Vector<Vertex>> components= new Vector<Vector<Vertex>>();
	 for (Vertex v:vertexSet)
		 v.clearDFSVisited();
	 boolean nodesExist= true;
	 while (nodesExist)
	 {
		 Vector<Vertex> component= new Vector<Vertex>();
		 Vertex vertexInComponent =new Vertex();
		 nodesExist=false;
		 for (Vertex v:vertexSet)
		 {
			if (!v.isDFSVisited())
			{
				vertexInComponent=v;
				nodesExist=true;
				break;
			}
		 }
		 if (nodesExist)
		 {
			component=this.VertexDFS(vertexInComponent);
			components.addElement(component);
			
		 }
		 
		 
	 }
	 for (Vertex v:vertexSet)
		 v.clearDFSVisited();
	 return components;
}

public Vector<Vertex> VertexDFS(Vertex first){
	 
	 Vector<Vertex> vertexList=new  Vector<Vertex>();
	 first.markDFSVisited();
	 vertexList.add(first);
	 for (Vertex next :first.getOutgoingNeighbors())
	 {
		 if (!next.isDFSVisited())
			 
			 {
			    vertexList=this.mergeList(this.VertexDFS(next),vertexList);
			 	//vertexList.add(next);
			 }
			 
	 }
	 return vertexList;
 }
 //********************************************************************************************
 
 
 public void cleanUp(){
	 for (Iterator<Vertex> iter= this.getVertices().iterator(); iter.hasNext();)
	 {
		 Vertex next=iter.next();
		 next.clearTags();
		 
	 }
 }



 
 
}
