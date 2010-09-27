

import java.util.TreeSet;
import java.util.Vector;
import java.util.HashSet;



class KruskalEdges
{
    private Vector<HashSet<Integer>> vertexGroups = new Vector<HashSet<Integer>>();
    private TreeSet<Edge> kruskalEdges = new TreeSet<Edge>();

    public TreeSet<Edge> getEdges()
    {
        return kruskalEdges;
    }
   
    HashSet<Integer> getVertexGroup(int vertexID)
    {
        for (HashSet<Integer> vertexGroup : vertexGroups) {
            if (vertexGroup.contains(vertexID)) {
                return vertexGroup;
            }
        }
        return null;
    }

  
    public void insertEdge(Edge edge)
    {
        int vertexA = edge.getVertexA().getID();
        int vertexB = edge.getVertexB().getID();

        HashSet<Integer> vertexGroupA = getVertexGroup(vertexA);
        HashSet<Integer> vertexGroupB = getVertexGroup(vertexB);

        if (vertexGroupA == null) {
            kruskalEdges.add(edge);
            if (vertexGroupB == null) {
                HashSet<Integer> htNewVertexGroup = new HashSet<Integer>();
                htNewVertexGroup.add(vertexA);
                htNewVertexGroup.add(vertexB);
                vertexGroups.add(htNewVertexGroup);
            }
            else {
                vertexGroupB.add(vertexA);        	
            }
        }
        else {
            if (vertexGroupB == null) {
                vertexGroupA.add(vertexB);
                kruskalEdges.add(edge);
            }
            else if (vertexGroupA != vertexGroupB) {
                vertexGroupA.addAll(vertexGroupB);
                vertexGroups.remove(vertexGroupB);
                kruskalEdges.add(edge);
            }
        }
    }
}

public class Kruskal
{   public  TreeSet<Edge> spanner(TreeSet<Edge> subgraph)
    {
        TreeSet<Edge> edges = subgraph;
        KruskalEdges vv = new KruskalEdges();

        for (Edge edge : edges) 
              vv.insertEdge(edge);
    
        return vv.getEdges();
    }
	
		
}
    


