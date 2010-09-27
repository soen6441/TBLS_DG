
import java.util.*;
import java.io.*;

public class PInOut_Unidirectional {

	public Vector<Vector<Vertex>> PInOutUniAlgo(Graph g, int locality) {
		Vector<Vector<Vertex>> CDASGroup = new Vector<Vector<Vertex>>();
		Vector<Vertex> nonFixedNodes = new Vector<Vertex>();
		Vector<Vertex> pendingNodes = new Vector<Vertex>();

		//Initially all the nodes are in the CDAS (Status = IN)
		for (Vertex v : g.getVertices()) 
			if (v.getStatus()==Constants.IN){			
				nonFixedNodes.add(v);
				v.restart();
			}

		// Select Seeds (all the nodes with minimum rank(Degree, ID) in their
		// neighborhood)
		for (Vertex v : g.getVertices())
			if (v.canStart())
				pendingNodes.add(v);

		/*System.out.print("Seeds are: ");
		for (Vertex v : pendingNodes)
			System.out.print(v.getID() + " ");
		System.out.println();*/
		int iteration = 0;
		for (int k = 1; k <= locality; k++) {
			while (!nonFixedNodes.isEmpty()) {
				/*
				 * System.out.print("Iteration "+iteration+": Pending nodes are: "
				 * ); for (Vertex v : pendingNodes) System.out.print(v.getID()+
				 * " ,"); System.out.println();
				 */
				Vector<Vertex> nodesReceivingTryRunningAlgoMsg = new Vector<Vertex>();
				for (Vertex v : pendingNodes) {
					// if all my lower-rank neighbors have finished the
					// algorithm
					if ((!v.isFinished()) && (v.canStart(k))) {
						nonFixedNodes.remove(v);
						v.markAsFinished();
						if (PInOutTest(v, k,g.getMaxTransmissionRange(),g.getMinTransmissionRange()))
							v.markOut();
					}
					for (Vertex u : v.calculateKHopN_a_U_N_d(k))
						if (!u.isFinished())
							nodesReceivingTryRunningAlgoMsg.add(u);
				}

				for (Vertex w : nodesReceivingTryRunningAlgoMsg)
					if (!w.isFinished() && (!w.isInList(pendingNodes)))
						pendingNodes.add(w);

				// cleanup Pending nodes
				Vector<Vertex> temp = new Vector<Vertex>();
				for (Vertex x : pendingNodes)
					if (!x.isFinished())
						temp.add(x);
				pendingNodes = temp;
				iteration++;
				//System.out.println("Number of nonfixed nodes: "+nonFixedNodes.size());

			}// end of while
			Vector<Vertex> CDAS = new Vector<Vertex>();
			for (Vertex v : g.getVertices()) {
				if (v.getStatus()== Constants.IN) {
					nonFixedNodes.add(v);
					v.restart();
					CDAS.add(v);
				}
			}
			//System.out.println("At the end of this round, the CDAS size is: "+CDAS.size());
			pendingNodes.clear();
			CDASGroup.add(CDAS);
			for (Vertex v : g.getVertices())
				if (v.canStart(k + 1))
					pendingNodes.add(v);

		}//end of for

		return CDASGroup;

	}

	public boolean PInOutTest(Vertex v, int k, int max_range, int min_range) {
		boolean flag = true;
		//domination and absorbency test
		//1.domination test: all the nodes that are dominated by me (N_a) should have at 
		// least another dominator
		for (Vertex u : v.getN_a()) 
			if (v.subtractFromSet(u.getPInOutDominators()).isEmpty()) 
				flag = false;
			
		//2. Absorbency test: all the nodes that are absorbed by me (N_d) should have at
		// least another absorbent
		for (Vertex u:v.getN_d())
			if (v.subtractFromSet(u.getPInOutAbsorbents()).isEmpty())
				flag = false;
		//Connectivity test: The subgraph induced by the set of nodes in the CDAS in my
		//k-hop neighborhood must be strongly connected
		if (flag)
		{
			Vector<Vertex> kHopNeighborsInPInOutCDAS = new Vector<Vertex>();
			kHopNeighborsInPInOutCDAS = v.getKHopNeighborsInPInOutCDAS(k);
			if (kHopNeighborsInPInOutCDAS.size() == 1 )
				return true;
			else if (new Graph(kHopNeighborsInPInOutCDAS,max_range,min_range).isStronglyConnected().getID() == -1)
				return true;					
		}		
		return false;
		
	}
	
	/*public static void main(String[] args)
	{
		Vector<Vector<Vertex>> CDASGroup = new Vector<Vector<Vertex>>();
		Graph g = new Graph(100,15, 45, 200, 200);
		if (g.isStronglyConnected().getID() == -1)
		{
			PInOut_Unidirectional PInOutUni = new PInOut_Unidirectional();
			
			CDASGroup = PInOutUni.PInOutUniAlgo(g, 4);
			for (Vector<Vertex> CDAS:CDASGroup)
			{
				System.out.println("CDAS size is: " +CDAS.size());
				if (new Graph(CDAS).isStronglyConnected().getID() == -1)
					System.out.println("CDAS is strongly connected!!!");
				else System.out.println("CDAS is NOT strongly connected!!!");
			}
			//testing the correctness
			boolean correct = true;
			for (Vertex v:g.getVertices())
			{
				if(v.getStatus() != Constants.IN)
				{
					boolean hasADominator = false;
					boolean hasAnAbsorbent = false;
					for (Vertex u:v.getN_a())
						if (u.getStatus() == Constants.IN)
							hasAnAbsorbent = true;
					for (Vertex u:v.getN_d())
						if (u.getStatus() == Constants.IN)
							hasADominator = true;
					if (!hasADominator)
					{
						System.out.println("Node "+v.getID()+" has no dominator");
						correct = false;						
					}
					if (!hasAnAbsorbent)
					{
						System.out.println("Node "+v.getID()+" has no absorbent");
						correct = false;
					}
				}
			}
			if (correct)
				System.out.println("Algorithm is CORRECT!!!");
			else System.out.println("Algorithm is INCORRECT :(((");
		}else System.out.println("The graph is not strongly connected");
		
	}*/
	
	   public static void main(String args[]) {
			try {
				for (int i = 0; i < 6; i++) {
					MyFile myFile = new MyFile(".\\Graphs(40-50)\\graphFile(40-50)" + i + ".txt");
					FileReader input = new FileReader(myFile.getFileName());
					BufferedReader bufRead = new BufferedReader(input);
					MyFile result = new MyFile(".\\PInout\\Graphs(40-50)\\PInOut(40-50)" + (i + 1) * 50
							+ ".txt");
					FileWriter fstream = new FileWriter(result.getFileName());
					BufferedWriter out = new BufferedWriter(fstream);

					out.write("CDAS size(k=1)"+ "\t"
									+"CDAS size(k=2)"+ "\t"
									+"CDAS size(k=3)"+ "\t"
									+"CDAS size(k=4)"+ "\t"
									+"CDAS size(k=5)"+ "\t"
									+"CDAS size(k=6)"+ "\t"
									+"CDAS size(k=7)"+ "\t"
									+"CDAS size(k=8)"+ "\t"
									+"CDAS size(k=9)"+ "\t"
									+"CDAS size(k=10)"+ "\t"
									+ "Percentage of unidirectional links (in Graph induced by CDAS)"
									+ "\t" + "Avg. Trans. range of nodes in CDAS"
									+ "\t" + "InDegree" + "\t" + "OutDegree" + "\t"
									+ "Bidirectional links" + "\t"
									+ "Node degree"+"\t"
									+ "Avg SPL on orig. graph" + "\t"
									+ "Avg SPL via CDAS"+"\t");
					out.write("\n");
					for (int j = 0; j < 100; j++) {
						Graph g = myFile.generateGraphFromFile(bufRead,40,50);
						PInOut_Unidirectional pinout = new PInOut_Unidirectional();
					    Vector<Vector<Vertex>> CDASGroup = new Vector<Vector<Vertex>>();
					    //run the algorithm for k=10
					    CDASGroup = pinout.PInOutUniAlgo(g, 10);
						   for (Vector<Vertex> CDAS:CDASGroup)
						   {
							   if ( new Graph(CDAS,g.getMaxTransmissionRange(),g.getMinTransmissionRange()).isStronglyConnected().getID()!=-1)
							   {
								   MyFile error=new MyFile("error"+i+".txt");
								   error.writeGraphToFile(g);
								   System.out.println("CDAS is NOT Strongly connected ");
								   out.close();
								   System.exit(0);
							   }
							   out.write(CDAS.size()+"\t");
						   }
						   //collect all the rest of the statistics on the k=1
						   Vector<Vertex> CDAS = CDASGroup.get(0);				
						
						Graph cdasg = new Graph(CDAS,g.getMaxTransmissionRange(),g.getMinTransmissionRange());
						if (cdasg.isStronglyConnected().getID() != -1) {
							MyFile error = new MyFile("error" + i + ".txt");
							error.writeGraphToFile(g);
							System.out.println("CDAS is disconnected!!!!!!!!!!!!!!!!!! ");
							out.close();
							System.exit(0);
						}
												
						// write the percentage of Unidirectional links in the graph induced by CDAS
						double unidirectional = 0;
						for (Edge e : cdasg.getEdges())
							if (!e.reverseEdge().isInListUnidirectional(
									cdasg.getEdges()))
								++unidirectional;
						double bidirectional = (cdasg.getEdges().size() - unidirectional) / 2;
						double unidirectionalPercentage = (unidirectional / (unidirectional + bidirectional)) * 100;
						out.write((int)unidirectionalPercentage+"\t");
						//For all the nodes in the CDAS, calculate:
						//Avg. transmission range, Average Incoming,outgoing,node degree and avg. num of bidirectional links
						double sumTransmissionRange =0;
						double sumOfIncoming = 0;
						double sumOfOutgoing = 0;
						double sumOfBidirectional = 0;
						double sumOfNeighbors = 0;
						for (Vertex v:CDAS)
						{
							sumTransmissionRange += v.getTransmissionRange();
							sumOfIncoming += v.getN_d().size();
							sumOfOutgoing += v.getN_a().size();
							sumOfBidirectional += v.getN_bi().size();
							sumOfNeighbors += v.getN_a_U_N_d().size();
						}
						double avgTransmissionRange = sumTransmissionRange/CDAS.size();
						double avgIncomingDegree = sumOfIncoming/CDAS.size();
						double avgOutgoingDegree = sumOfOutgoing/CDAS.size();
						double avgBidirectional = sumOfBidirectional/CDAS.size();
						double avgNumOfNeighbros = sumOfNeighbors/CDAS.size();
						//write the results
						out.write((int)avgTransmissionRange+"\t");
						out.write((int)avgIncomingDegree+"\t");
						out.write((int)avgOutgoingDegree+"\t");
						out.write((int)avgBidirectional+"\t");
						out.write((int)avgNumOfNeighbros+"\t");
						//calculate the Average Shortest Path Length (ASPL) on the original graph
						// and via the SCDAS
						Dijkstra dijkstra = new Dijkstra();
						Ratios r = dijkstra.calculateRatio(g, CDAS);
						out.write((int)r.getAverageHopLengthViaCDS()+"\t");
						out.write((int)r.getAverageHopLengthOnGraph()+"\t");
											
						out.write("\n");

					}
					System.out.println("simulation ended for series " + i);
					out.close();

				}
			} catch (IOException e) {
				// If another exception is generated, print a stack trace
				e.printStackTrace();
			}
		}


	

}