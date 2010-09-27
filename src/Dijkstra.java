import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Dijkstra {
	
		
	private ArrayList<ArrayList<Integer>> calculateHopDistance(Graph g, int numOfVertices)
	{
		ArrayList<ArrayList<Integer>> graphHopDistance = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Double>> graphEuclideanDistance = new ArrayList<ArrayList<Double>>();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i=0; i< numOfVertices; ++i)
			temp.add(1000000);
		
		
		for (int i=0; i< numOfVertices; ++i)
			graphHopDistance.add(temp);
		
		for (Vertex v:g.getVertices())
			graphHopDistance.set(v.getID(), hopDijkstra(g, v, numOfVertices));
		
				
		return graphHopDistance;
	}


	private ArrayList<Integer> hopDijkstra(Graph g,Vertex source, int numOfVertices) {
		ArrayList<Integer> distanceToEveryOtherNode = new ArrayList<Integer>();
		ArrayList<Vertex> queue = new ArrayList<Vertex>();
		for (int i=0; i< numOfVertices;++i)
			distanceToEveryOtherNode.add(1000000);
		
		
		for (int i=0; i<g.getVertices().size(); ++i)
		{
			queue.add(g.getVertices().get(i));
						
		}
		distanceToEveryOtherNode.set(source.getID(), 0);
		
		boolean foundAtLeastOneCandidate;
		while (!queue.isEmpty()) {
			// find the vertex in q with minimum distance to source
			Vertex candidate = new Vertex(-1, 0, 0, -1);
			foundAtLeastOneCandidate = false;
			for (Vertex u : queue) {

				if (distanceToEveryOtherNode.get(u.getID())!= 1000000) {
					if (!foundAtLeastOneCandidate) {

						candidate = u;
						foundAtLeastOneCandidate = true;

					} else if (distanceToEveryOtherNode.get(candidate.getID())> distanceToEveryOtherNode.get(u.getID())) {
						candidate = u;

					}
				}

			}

		
			if (!foundAtLeastOneCandidate) {
				System.out
						.println("Error in calculating the shortest path, graph is disconnected :(");
				System.exit(0);
			}

			else {
				queue.remove(candidate);
				for (Vertex v : candidate.getOutgoingNeighbors())
					if ((distanceToEveryOtherNode.get(v.getID()) == 1000000)
							|| (distanceToEveryOtherNode.get(v.getID()) > distanceToEveryOtherNode.get(candidate.getID())+1)) {
						distanceToEveryOtherNode.set(v.getID(), distanceToEveryOtherNode.get(candidate.getID())+1);

					}
			}
		}
		return distanceToEveryOtherNode;

	}
	public Ratios calculateRatio(Graph g, Vector<Vertex> CDSVector)
	{
	
		Graph CDSGraph = new Graph(CDSVector, g.getMaxTransmissionRange(),g.getMinTransmissionRange());
					
		ArrayList<ArrayList<Integer>> hopDistanceOnOriginalGraph = calculateHopDistance(g, g.getVertices().size());
		ArrayList<ArrayList<Integer>> hopDistanceOnCDS = calculateHopDistance(CDSGraph, g.getVertices().size());
		ArrayList<ArrayList<Integer>> hopDisViaCDSOnOriginalGraph= new ArrayList<ArrayList<Integer>>();
			   
		for (Vertex v:g.getVertices())
		{
			ArrayList<Integer> temp = new ArrayList<Integer>();
			for (Vertex u:g.getVertices())
				temp.add(1000000);
			hopDisViaCDSOnOriginalGraph.add(temp);
		}
		
		int minHop;
	     for (Vertex v: g.getVertices())
	     	for (Vertex u: g.getVertices())
	     	{
	     		//the first vertex is in CDS
	     		if (v.isInList(CDSVector))
	     		{
	     			
	     			//the second is also in CDS
	     			if (u.isInList(CDSVector))
	     			{
	     				ArrayList<Integer> temp= hopDisViaCDSOnOriginalGraph.get(v.getID());
	     				temp.set(u.getID(), hopDistanceOnCDS.get(v.getID()).get(u.getID()));
	     				hopDisViaCDSOnOriginalGraph.set(v.getID(),temp); 
	     			}
	     			//the second is not in CDS
	     			else
	     			{
	     				minHop = 1000000;
	     				for (Vertex w:u.getOutgoingNeighbors())
	     				{
	     					if (w.isInList(CDSVector))
	     						if (minHop > hopDistanceOnCDS.get(v.getID()).get(w.getID()))
	     							minHop =hopDistanceOnCDS.get(v.getID()).get(w.getID());
	     				}
	     				if (minHop==1000000)
	     					System.out.println("CDS is not really a DS ");
	     				ArrayList<Integer> temp= hopDisViaCDSOnOriginalGraph.get(v.getID());
	     				temp.set(u.getID(), minHop+1);
	     				hopDisViaCDSOnOriginalGraph.set(v.getID(),temp); 
	     				
	     			}
	     		}else //the first Vertex is not in CDS
	     		{
	     			//the second is in CDS
						if (u.isInList(CDSVector)) {
							
							minHop = 1000000;
							for (Vertex w : v.getOutgoingNeighbors()) {
								if (w.isInList(CDSVector))
		     						if (minHop > hopDistanceOnCDS.get(u.getID()).get(w.getID()))
		     							minHop =hopDistanceOnCDS.get(u.getID()).get(w.getID());
								
							}
							if (minHop==1000000)
		     					System.out.println("CDS is not really a DS ");
							ArrayList<Integer> temp= hopDisViaCDSOnOriginalGraph.get(v.getID());
		     				temp.set(u.getID(), minHop+1);
		     				hopDisViaCDSOnOriginalGraph.set(v.getID(),temp); 
		     				
	     				
						}
						else //the second is not in CDS either
						{
							minHop = 1000000;
							for (Vertex w: v.getOutgoingNeighbors())
								for (Vertex z: u.getOutgoingNeighbors())
								{
									if ((w.isInList(CDSVector))&&(z.isInList(CDSVector)))
									{   
										if (minHop > hopDistanceOnCDS.get(w.getID()).get(z.getID()))
			     							minHop =hopDistanceOnCDS.get(w.getID()).get(z.getID());
									}
								}
							if (minHop==1000000)
		     					System.out.println("CDS is not really a DS ");
							ArrayList<Integer> temp= hopDisViaCDSOnOriginalGraph.get(v.getID());
		     				temp.set(u.getID(), minHop+2);
		     				hopDisViaCDSOnOriginalGraph.set(v.getID(),temp); 
	     				
						}
	     			
	     		}
	     			
	     	}// End of for loops
	   		
	     //compare hop distance on CDS with original graph
	     for (int i=0; i < g.getVertices().size()-1; ++i)
		     	for (int j= i+1; j < g.getVertices().size(); ++j)
		     	{
		     		/*if (hopDistanceOnCDS.get(i).get(j)<hopDistanceOnOriginalGraph.get(i).get(j))
		     		{
		     			System.out.println("i= "+i+", j= "+j);
	     			    System.out.println( "hop Distance On Original Graph is "+hopDistanceOnOriginalGraph.get(i).get(j)+ " = ? "+hopDistanceOnOriginalGraph.get(j).get(i));
	     			    System.out.println( "hop Distance on CDS is "+hopDistanceOnCDS.get(i).get(j)+ " = ? "+hopDistanceOnCDS.get(j).get(i));
	     			    System.out.println("Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr!");
	     				
	     			}*/
		     	}
	     //compute the average ratio of path length 
	     double sumOfhopLengthOnGraph=0;
	     double sumOfhopLengthViaCDS=0;
	     double averageSumOfhopLengthOnGraph=0;
	     double averageSumOfhopLengthViaCDS=0;
	     
	     for (int i=0; i < g.getVertices().size()-1; ++i)
	     	for (int j= i+1; j < g.getVertices().size(); ++j)
	     	{
	     		sumOfhopLengthOnGraph += hopDistanceOnOriginalGraph.get(i).get(j);
	     		sumOfhopLengthViaCDS+=hopDisViaCDSOnOriginalGraph.get(i).get(j);
	     		/*if (hopDistanceOnOriginalGraph.get(i).get(j) > hopDisViaCDSOnOriginalGraph.get(i).get(j))
	     		
	     			{
	     			
	     			System.out.println("i= "+i+", j= "+j);
	     			    System.out.println( "hop Distance On Original Graph is "+hopDistanceOnOriginalGraph.get(i).get(j) );
	     			   System.out.println( "hop Distance via CDS is "+hopDisViaCDSOnOriginalGraph.get(i).get(j));
	     			    System.out.println("Errorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr!");
	     				//System.exit(0);
	     			}*/
	     			
	     	}
	     averageSumOfhopLengthOnGraph = (2*sumOfhopLengthOnGraph) / ((g.getVertices().size()-1)*g.getVertices().size());
	     averageSumOfhopLengthViaCDS=(2*sumOfhopLengthViaCDS) / ((g.getVertices().size()-1)*g.getVertices().size());
	     //System.out.println("ASPL on the original graph is: "+averageSumOfhopLengthOnGraph);
	     //System.out.println("ASPL on the CDS is: "+averageSumOfhopLengthViaCDS);
	     return new Ratios(averageSumOfhopLengthOnGraph, averageSumOfhopLengthViaCDS);
	}
	
	
	
//******************************************************************************************		
//*********************************************** G_CMA *************************************	
//** added to calculate and return the NODES and not merely the distance on the shortest path
	
	public Vector<Vector<Vertex>> calculateShortestPathToEveryNode (Graph g, Vertex source, int numOfVertices)
	{
		Vector<Vector<Vertex>> ShortestPathToEveryOtherNode = new Vector<Vector<Vertex>>();
		Vector<Vertex> queue = new Vector<Vertex>();
		for (int i=0; i< numOfVertices;++i)
		{
			Vector<Vertex> temp = new Vector<Vertex>();
			temp.add(new Vertex(1000000, 0,0,0));
			ShortestPathToEveryOtherNode.add(temp);
		}
		
		
		for (int i=0; i<g.getVertices().size(); ++i)
		{
			queue.add(g.getVertices().get(i));
						
		}
		//every node is the shortest path to itself
		//so remove the dummy vertex with ID=1000000 and replace it with the vertex itself
		ShortestPathToEveryOtherNode.get(source.getID()).clear();
		ShortestPathToEveryOtherNode.get(source.getID()).add(source);
		
		
		boolean foundAtLeastOneCandidate;
		while (!queue.isEmpty()) {
			// find the vertex in q with minimum distance to source
			Vertex candidate = new Vertex(-1, 0, 0, -1);
			foundAtLeastOneCandidate = false;
			for (Vertex u : queue) {

				if (ShortestPathToEveryOtherNode.get(u.getID()).firstElement().getID()!= 1000000) {
					if (!foundAtLeastOneCandidate) {

						candidate = u;
						foundAtLeastOneCandidate = true;

					} else if (ShortestPathToEveryOtherNode.get(candidate.getID()).size()> ShortestPathToEveryOtherNode.get(u.getID()).size()) {
						candidate = u;

					}
				}

			}
			if (!foundAtLeastOneCandidate) {
				System.out
						.println("Error in calculating the shortest path, graph is disconnected :(");
				System.exit(0);
			}

			else {
				queue.remove(candidate);
				for (Vertex v : candidate.getOutgoingNeighbors())
					if ((ShortestPathToEveryOtherNode.get(v.getID()).firstElement().getID() == 1000000)
							|| (ShortestPathToEveryOtherNode.get(v.getID()).size() > ShortestPathToEveryOtherNode.get(candidate.getID()).size()+1))
					{
						ShortestPathToEveryOtherNode.get(v.getID()).clear();
						for (Vertex x:ShortestPathToEveryOtherNode.get(candidate.getID()))
							ShortestPathToEveryOtherNode.get(v.getID()).add(x);
						//in order to prevent the source  to appear twice on the path
						if (!ShortestPathToEveryOtherNode.get(v.getID()).contains(candidate))
							ShortestPathToEveryOtherNode.get(v.getID()).add(candidate);
					}
			}
		}
		return ShortestPathToEveryOtherNode;
	}
	public static void main (String[] args)
	{
		try{
		MyFile myFile=new MyFile("error0.txt"); 
		  FileReader input = new FileReader(myFile.getFileName());
		  BufferedReader bufRead = new BufferedReader(input); 
		  Graph g = myFile.generateGraphFromFile(bufRead,10,50);
		Dijkstra dk = new Dijkstra();
		if (g.isStronglyConnected().getID() == -1)
		{
			System.out.println("Graph is strongly connected");
			System.out.println("Enter the source vertex ID: ");
			Scanner in = new Scanner(System.in);
			int id = in.nextInt();
			Vertex source = new Vertex();
			while (id != -1) {
				for (Vertex v:g.getVertices())
					if (v.getID() == id)
					{
						source = v;
						break;
					}
						
				Vector<Vector<Vertex>> paths = dk.calculateShortestPathToEveryNode(g, source, g.getVertices().size());
				for (Vertex v : g.getVertices()) {
					System.out.println("Shortest path between "
							+ id + " and "
							+ v.getID() + " :");
					for (Vertex u : paths.get(v.getID()))
						System.out.print(u.getID() + " ,");
					System.out.println();

				}
				System.out.println("Enter the source vertex ID: ");
				id = in.nextInt();
			}
		}else System.out.println("Graph NOT strongly connected");
		}catch(IOException e){e.printStackTrace();}
	}
	
	
	
}//end of class	
	
	    
	   