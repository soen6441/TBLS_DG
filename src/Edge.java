import java.util.TreeSet;
import java.util.Vector;

class Edge implements Comparable<Edge>
{
    private double length;
    private double x1,x2,y1,y2;
    private Vertex vertex1, vertex2;
    
    
    
    public Edge(Vertex v1, Vertex v2 )
    {
    	this.length=v1.EuclideanDistance(v2);
    	this.x1=v1.getX();
        this.x2=v2.getX();
        this.y1=v1.getY();
        this.y2=v2.getY();
        this.vertex1=v1;
	    this.vertex2=v2;
    }

  /*  public Edge(String vertexA, String vertexB, int weight)
    {
        this.vertexA = vertexA;
        this.vertexB = vertexB;
        this.weight = weight;
    }

  */  
    
    public Vertex getVertexA()
    {
        return this.vertex1;
    }

    public Vertex getVertexB()
    {
        return this.vertex2;
    }
	
       

    public double getWeight()
    {
        return this.length;
    }
    
    public Edge reverseEdge()
    {
    	return (new Edge(this.getVertexB(), this.getVertexA()));
    }
    
   

    @Override
    public String toString()
    {
        return "(" + this.vertex1.getID() + ", " + this.vertex2.getID() + ") : Weight = " + roundUp(this.length);
    }
    
    public boolean isInList(TreeSet<Edge> edges)
    {
    	 boolean found=false;
    	 for (Edge edge : edges)
    		 if (((edge.getVertexA().getID()==this.getVertexA().getID())&&    (edge.getVertexB().getID()==this.getVertexB().getID()))||    ((edge.getVertexA().getID()==this.getVertexB().getID())&&    (edge.getVertexB().getID()==this.getVertexA().getID())))
    		 {	 
    			 found=true;
    		 	 break;
    		 }
    	 return found;
    	 
    }
    
	public boolean isInListUnidirectional(TreeSet<Edge> edges) {
		boolean found = false;
		for (Edge edge : edges)
			if (((edge.getVertexA().getID() == this.getVertexA().getID()) && (edge
					.getVertexB().getID() == this.getVertexB().getID()))
					) {
				found = true;
				break;
			}
		return found;

	}
    
    
    private double roundUp(double a)
    {
    	double x = Math.round(a * 100);
    	return x/100;
    }
    


    public int compareTo(Edge edge)
    {
    	if (this.length != edge.length)
		return (this.length < edge.length) ? -1: 1;
      else
           if (this.x1 != edge.x1)
		return (this.x1 < edge.x1) ? -1: 1;
      else if (this.y1 != edge.y1)
		return (this.y1 < edge.y1) ? -1: 1;
      else if (this.x2 != edge.x2)
		return (this.x2 < edge.x2) ? -1: 1;
	else  return (this.y2 < edge.y2) ? -1: 1;





    }

  

}

