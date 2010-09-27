import java.util.*;

//Find a Dominating and Absorbent Set (FDAS)
//This class is used by both Greedy Spider Contraction Algorithm and Greedy Strongly Connected
//Component Merging Algorithm 
public class FDAS {

	public Vector<Vertex> findDAS(Graph g)
	{
		//S will eventually constitute the Dominating and Absorbent Set of Graph g
		Vector<Vertex> S = new Vector<Vertex>();
		Vector<Vertex> unColoredNodes = new Vector<Vertex>(); 
		//Initially all nodes are uncolored
		for (Vertex v:g.getVertices())
			v.unColor();
		
		//1. Find a dominating set
		//Initially all the nodes are uncolored and thus in the "unColored nodes" vector  
		for (Vertex v:g.getVertices())
			unColoredNodes.add(v);
		
		while (!unColoredNodes.isEmpty())
		{
			//find an uncolored node u with policy 2 (maxDegree)
			Vertex u = maxDegreeNode(unColoredNodes);
			//color u in BLACK
			u.markBlack();
			unColoredNodes.remove(u);
			//S <- S U {u}
			S.add(u);
			// for v is an outgoing adjacent neighbor of u do 
			// if v is an uncolored node then color v GRAY 
			for (Vertex v:u.getN_a())
				if (v.isUnColored())
				{
					v.markGray();
					unColoredNodes.remove(v);
				}
		}
		//2. Find an absorbent set */STATE/* pre-processing*/
		Vector<Vertex> blackNodes = new Vector<Vertex>();
		for (Vertex v:g.getVertices())
			if (v.isBlack())
				blackNodes.add(v);
		for (Vertex v:blackNodes)
			for (Vertex w:v.getN_d())
				if (w.isGray())
					w.markWhite();
		//3. /*Main processing*/
		Vector<Vertex> grayNodes = new Vector<Vertex>();
		for (Vertex v:g.getVertices())
			if (v.isGray())
				grayNodes.add(v);
		while (!grayNodes.isEmpty())
		{
			Vertex w = nodeWithMaxIncomingGrayNeighbors(grayNodes);
			w.markBlack();
			grayNodes.remove(w);
			//S <- S U {w}
			S.add(w);
			for (Vertex x:w.getN_d())
				if (x.isGray())
				{
					x.markWhite();
					grayNodes.remove(x);
				}
		}
		return S;
	}
	public Vertex maxDegreeNode(Vector<Vertex> vertices)
	{
		Vertex maxDegreeNode = new Vertex();
		maxDegreeNode = vertices.firstElement();
		for (Vertex v:vertices)
			if (v.getUnidirectionalDegree() > maxDegreeNode.getUnidirectionalDegree())
				maxDegreeNode = v;
		return maxDegreeNode;
	}
	public Vertex nodeWithMaxIncomingGrayNeighbors(Vector<Vertex> vertices)
	{
		Vertex nodeWithMaxIncomingGrayNeighbors = new Vertex();
		nodeWithMaxIncomingGrayNeighbors = vertices.firstElement();
		for (Vertex v:vertices)
			if (v.getNumOfIncomingGrayNeighbors()>
					nodeWithMaxIncomingGrayNeighbors.getNumOfIncomingGrayNeighbors())
				nodeWithMaxIncomingGrayNeighbors = v;
		return nodeWithMaxIncomingGrayNeighbors;
			
	}
	public void Test()
	{
		Graph g = new Graph(150, 20, 40, 200, 200);
		if (g.isStronglyConnected().getID() == -1)
		{
			System.out.println("Graph is Strongly connected");
			FDAS fdas = new FDAS();
			Vector<Vertex> complementSet = new Vector<Vertex>();
			Vector<Vertex> DAS = fdas.findDAS(g);
			System.out.println("The size of the DAS is: "+DAS.size());
			for (Vertex v:DAS)
				v.markBlack();
			for (Vertex v:g.getVertices())
				if (!v.isBlack())
					complementSet.add(v);
			boolean ok = true;
			for (Vertex v:complementSet)
			{
				boolean dominated= false;
				boolean absorbed = false;
				for (Vertex u:v.getN_a())
				{
					if (u.isBlack())
					{
						absorbed = true;
						break;
					}
				}
				if (!absorbed)
				{
					System.out.println("Node "+v.getID()+" is not bsorbed!!!");
					ok = false;
				}
				for (Vertex u:v.getN_d())
				{
					if (u.isBlack())
					{
						dominated = true;
						break;
					}
				}
				if (!dominated)
				{
					System.out.println("Node "+v.getID()+" is not dominated!!!");
					ok = false;
				}
			}
			if (ok)
				System.out.println("DAS is both dominating and absorbent!!!");
			else
				System.out.println("DAS is NOT dominating and absorbent:((((((");
		}
		else System.out.println("Graph is not strongly connected");
	}
}
