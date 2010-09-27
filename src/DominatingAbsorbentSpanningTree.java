import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class DominatingAbsorbentSpanningTree {
	Vector<Vector<Vertex>> CDAS;
	
	public  DominatingAbsorbentSpanningTree(){
		this.CDAS = new Vector<Vector<Vertex>>();
		
	}
	public Vector<Vector<Vertex>> DAST (Graph g)
	{
		Vector<Vertex> DT = new Vector<Vertex>();
		Vector<Vertex> AT = new Vector<Vertex>();
		Vector<Vertex> Temp = new Vector<Vertex>();
		//arbitrarily select a node r
		Vertex r = g.getVertices().firstElement();
		// DT = ConsTree((V,E),r)
		DT = this.ConsTree(g, r);
		
		//reverse all directed edges in E and form a new edge set E'
		Graph transposeG = g.transposeGraph();
		//select the same node as the root of the transpose tree
		int id = r.getID();
		Vertex rTrans = new Vertex();
		for (Vertex v:transposeG.getVertices())
			if (id == v.getID())
			{
				rTrans = v;
				break;
			}
		//AT = ConsTree ((V,E'),r)
		Temp = this.ConsTree(transposeG, rTrans);
		for (Vertex v:Temp)
			for (Vertex u:g.getVertices())
				if (v.getID() == u.getID())
					AT.add(u);
				
		// CDAS = DT U AT (ignoring redundant nodes)
		CDAS.add(DT);
		CDAS.add(AT);
		return CDAS;
	}

	private Vector<Vertex> ConsTree(Graph g, Vertex r)
	{
		Vector<Vertex> DominatingTree = new Vector<Vertex>();
		Vector<Vertex> D = new Vector<Vertex>();
		//color all nodes white
		for (Vertex v:g.getVertices())
			v.markWhite();
		//D <- {r}
		D.add(r);
		Vertex v= new Vertex();
		while (!D.isEmpty())
		{
			for (Vertex d:D)
			{
				//pick the first while node in D if there exists one
				if (d.isWhite())
				{
					v = d;
					break;
				}
			}
			//if there is no more white node in D, then break out of the while loop
			if (!v.isWhite()){
				break;
			}
			v.markBlack();
			for (Vertex u:v.getN_a())
				if (u.isWhite())
				{
					u.markGray();
					for (Vertex s:u.getN_a())
						if ((s.isWhite()) && (!s.isInList(D)))
						{
							D.add(s);
							s.setParent(u);
						}//end if
				}//end for
			if (v.getID() != r.getID())
			{
				v.getParent().markBlue();
				D.remove(v);
			}//end if
		}//end while
		for (Vertex vertex:g.getVertices())
			if (vertex.isBlack() || vertex.isBlue())
				DominatingTree.add(vertex);
		return DominatingTree;
	}
	/*public void Test(){
		
		Graph g = new Graph(150,30, 45, 200, 200);
		Vector<Vertex> CDAS = new Vector<Vertex>();
		Vector<Vector<Vertex>> DTAT = new Vector<Vector<Vertex>>();
		  
		
		DominatingAbsorbentSpanningTree dast = new DominatingAbsorbentSpanningTree();
		if (g.isStronglyConnected().getID() == -1) {
			System.out.println("graph is Strongly connected");
		
			DTAT = dast.DAST(g);
			System.out.println("DT's size is:"+DTAT.get(0).size());
			System.out.println("AT's size is:"+DTAT.get(1).size());
			for (Vertex v:DTAT.get(0))
				CDAS.add(v);
			for (Vertex v:DTAT.get(1))
				if (!v.isInList(CDAS))
					CDAS.add(v);
		
			System.out.println("CDAS's size is: "+CDAS.size());
			Vertex temp = g.isDominatingAndAbsorbent(DTAT.get(0), DTAT.get(1));
			if (temp.getID() == -1)
				System.out.println("The resulting CDAS is indeed both dominating and absorbent!!!!");
			else {System.out.println("The CDAS is NOT dominating and absorbent:((((((((((");
				System.out.println("Node "+temp.getID()+" is not dominated or absorbed!");
				System.out.println("N_d: ");
				for (Vertex u:temp.getN_d())
					System.out.print(u.getID()+", "+u.isDominatorOrAbsorbent()+" ,");
				System.out.println();
				System.out.println("N_a: ");
				for(Vertex u:temp.getN_a())
					System.out.print(u.getID()+", "+u.isDominatorOrAbsorbent()+" ,");
				System.out.println();
			}
			Graph CDASGraph = new Graph(CDAS,g.getMaxTransmissionRange(),g.getMinTransmissionRange());
			if (CDASGraph.isStronglyConnected().getID() == -1)
				System.out.println("CDAS IS strongly connected!!!!!!");
			else System.out.println("CDAS is not strongly connected");
		}
			else System.out.println("Graph is not strongly connected");
		
		
	}*/
	
	public static void main(String args[]) {
		try {
			for (int i = 0; i < 6; i++) {
				MyFile myFile = new MyFile(".\\Graphs(10-50)\\graphFile(10-50)" + i + ".txt");
				FileReader input = new FileReader(myFile.getFileName());
				BufferedReader bufRead = new BufferedReader(input);
				MyFile result = new MyFile(".\\DAST\\Graphs(10-50)\\DAST(10-50)" + (i + 1) * 50
						+ ".txt");
				FileWriter fstream = new FileWriter(result.getFileName());
				BufferedWriter out = new BufferedWriter(fstream);

				out.write("CDAS size"
								+ "\t"
								+ "Percentage of unidirectional links (in Graph induced by CDAS)"
								+ "\t" + "Avg. Trans. range of nodes in CDAS"
								+ "\t" + "InDegree" + "\t" + "OutDegree" + "\t"
								+ "Bidirectional links" + "\t"
								+ "Node degree"+"\t"
								+ "Avg SPL on orig. graph" + "\t"
								+ "Avg SPL via CDAS"+"\t");
				out.write("\n");
				for (int j = 0; j < 100; j++) {
					Graph g = myFile.generateGraphFromFile(bufRead,50,10);
					DominatingAbsorbentSpanningTree dast = new DominatingAbsorbentSpanningTree();
					Vector<Vector<Vertex>> DTAT = dast.DAST(g);
					Vector<Vertex> CDAS = new Vector<Vertex>();
					for (Vertex v : DTAT.get(0))
						CDAS.add(v);
					for (Vertex v : DTAT.get(1))
						if (!v.isInList(CDAS))
							CDAS.add(v);

					Graph cdasg = new Graph(CDAS,g.getMaxTransmissionRange(),g.getMinTransmissionRange());
					if (cdasg.isStronglyConnected().getID() != -1) {
						MyFile error = new MyFile("error" + i + ".txt");
						error.writeGraphToFile(g);
						System.out.println("graph is disconnected ");
						out.close();
						System.exit(0);
					}
					// write the size of the SCDAS to the file
					out.write(CDAS.size() + "\t");
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
