
import java.util.*;
import java.io.*;

public class PInOut_Unidirectional_localityInfo {

	public PInOutInfo PInOutUniAlgo(Graph g, int locality) {
		PInOutInfo pinoutInfo = new PInOutInfo();
		Vector<Vector<Vertex>> CDASGroup = new Vector<Vector<Vertex>>();
		Vector<Vertex> nonFixedNodes = new Vector<Vertex>();
		Vector<Vertex> pendingNodes = new Vector<Vertex>();
		Vector<Vector<Vertex>> initiators = new Vector<Vector<Vertex>>();
		Vector<Integer> numOfRoundsSet = new Vector<Integer>();
		int numOfRounds = 0;
		//Initially all the nodes are in the CDAS (Status = IN)
		for (Vertex v : g.getVertices()) {
			v.markIn();
			nonFixedNodes.add(v);
			v.restart();
		}

		// Select Seeds (all the nodes with minimum rank(Degree, ID) in their
		// neighborhood)
		for (Vertex v : g.getVertices())
			if (v.canStart())
				pendingNodes.add(v);
		
		/*System.out.println("Seeds are: ");
		for (Vertex v:pendingNodes)
			System.out.print(v.getID()+" ,");
		System.out.println();
*/
		
		int iteration = 0;
		for (int k = 1; k <= locality; k++) {
			Vector<Vertex> initiatorList = new Vector<Vertex>();
			for (Vertex v:pendingNodes)
				initiatorList.add(v);
			initiators.add(initiatorList);
			
			while (!nonFixedNodes.isEmpty()) {
								
				Vector<Vertex> nodesReceivingTryRunningAlgoMsg = new Vector<Vertex>();
				for (Vertex v : pendingNodes) {
					// if all my lower-rank neighbors have finished the
					// algorithm
					if ((!v.isFinished()) && (v.canStart(k))) {
						nonFixedNodes.remove(v);
						v.markAsFinished();
						if (PInOutTest(v, k , g.getMaxTransmissionRange(),g.getMinTransmissionRange()))
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
				++ numOfRounds;
				//System.out.println("Number of nonfixed nodes: "+nonFixedNodes.size());

			}// end of while
			numOfRoundsSet.add(numOfRounds);
			numOfRounds = 0;
			//restart the nodes in the CDAS and have them run the algo for the next 'k'
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
			for (Vertex v :CDAS)
				if (v.canStart(k + 1))
					pendingNodes.add(v);
			
			

		}//end of for
		
		//fill in all the info
		pinoutInfo.setCDASGroup(CDASGroup);
		pinoutInfo.setInitiators(initiators);
		pinoutInfo.setNumOfRounds(numOfRoundsSet);
		
		return pinoutInfo;

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
		//Vector<Vector<Vertex>> CDASGroup = new Vector<Vector<Vertex>>();
		PInOutInfo pinoutInfo = new PInOutInfo();
		Graph g = new Graph(100,25, 45, 200, 200);
		if (g.isStronglyConnected().getID() == -1)
		{
			PInOut_Unidirectional_localityInfo PInOutUniInfo = new PInOut_Unidirectional_localityInfo();
			
			pinoutInfo = PInOutUniInfo.PInOutUniAlgo(g, 1);
			for (Vector<Vertex> CDAS:pinoutInfo.getCDASGroup())
			{
				System.out.println("CDAS size is: " +CDAS.size());
				if (new Graph(CDAS).isStronglyConnected().getID() == -1)
					System.out.println("CDAS is strongly connected!!!");
				else System.out.println("CDAS is NOT strongly connected!!!");
			}
			System.out.println("num of initiators: "+pinoutInfo.getInitiators().get(0).size());
			System.out.println("num of rounds: "+pinoutInfo.getNumOfRounds().get(0));
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
		
	}
	*/
	   public static void main(String args[]) {
			try {
				for (int i = 0; i < 6; i++) {
					MyFile myFile = new MyFile(".\\Graphs(10-50)\\graphFile(10-50)" + i + ".txt");
					FileReader input = new FileReader(myFile.getFileName());
					BufferedReader bufRead = new BufferedReader(input);
					MyFile result = new MyFile(".\\PInout\\Graphs(10-50)\\Info(10-50)" + (i + 1) * 50
							+ ".txt");
					FileWriter fstream = new FileWriter(result.getFileName());
					BufferedWriter out = new BufferedWriter(fstream);

					out.write("# of Inits(k=1)"+ "\t"
									+"# of Inits(k=2)"+ "\t"
									+"# of Inits(k=3)"+ "\t"
									+"# of Inits(k=4)"+ "\t"
									+"# of Inits(k=5)"+ "\t"
									+"# of Inits(k=6)"+ "\t"
									+"# of Inits(k=7)"+ "\t"
									+"# of Inits(k=8)"+ "\t"
									+"# of Inits(k=9)"+ "\t"
									+"# of Inits(k=10)"+ "\t"
									+"# of Rounds(k=1)"+ "\t"
									+"# of Rounds(k=2)"+ "\t"
									+"# of Rounds(k=3)"+ "\t"
									+"# of Rounds(k=4)"+ "\t"
									+"# of Rounds(k=5)"+ "\t"
									+"# of Rounds(k=6)"+ "\t"
									+"# of Rounds(k=7)"+ "\t"
									+"# of Rounds(k=8)"+ "\t"
									+"# of Rounds(k=9)"+ "\t"
									+"# of Rounds(k=10)"+ "\t"
									);
					out.write("\n");
					for (int j = 0; j < 100; j++) {
						Graph g = myFile.generateGraphFromFile(bufRead,50,10);
						PInOut_Unidirectional_localityInfo pinout_Uni_Info = new PInOut_Unidirectional_localityInfo();
						PInOutInfo pinoutInfo = new PInOutInfo();						
					    //Vector<Vector<Vertex>> CDASGroup = new Vector<Vector<Vertex>>();
					    //run the algorithm for k=10
					    pinoutInfo = pinout_Uni_Info.PInOutUniAlgo(g, 10);
						   /*for (Vector<Vertex> CDAS:pinoutInfo.getCDASGroup())
						   {
							   if ( new Graph(CDAS).isStronglyConnected().getID()!=-1)
							   {
								   MyFile error=new MyFile("error"+i+".txt");
								   error.writeGraphToFile(g);
								   System.out.println("CDAS is NOT Strongly connected ");
								   out.close();
								   System.exit(0);
							   }
							   out.write(CDAS.size()+"\t");
						   }*/
						   for (Vector<Vertex> initiators:pinoutInfo.getInitiators())
						   {
							   out.write(initiators.size()+"\t");
						   }
						   for (int numOfRounds:pinoutInfo.getNumOfRounds())
						   {
							   out.write(numOfRounds+"\t");
						   }
						   //collect all the rest of the statistics on the k=1
						   Vector<Vertex> CDAS = pinoutInfo.getCDASGroup().get(0);				
						
						Graph cdasg = new Graph(CDAS,g.getMaxTransmissionRange(),g.getMinTransmissionRange());
						if (cdasg.isStronglyConnected().getID() != -1) {
							MyFile error = new MyFile("error" + i + ".txt");
							error.writeGraphToFile(g);
							System.out.println("CDAS is disconnected!!!!!!!!!!!!!!!!!! ");
							out.close();
							System.exit(0);
						}
						
											
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