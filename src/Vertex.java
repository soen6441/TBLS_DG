import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;


class Vertex {
 private int ID;
 private double transmissionRange;
 private double x,y; 
 private int classNum;
 private boolean visited;
 private boolean isDominatorOrAbsorbent;
 private boolean isDominatorOrAbsorbentSelected;
 private boolean DFSVisited;
 private boolean dijkstraMarker;
 private boolean WuMarker;
 Vector<Vertex> incommingNeighborList;
 Vector<Vertex> outgoingNeighborList;
 Vector<Vertex> N_d;
 Vector<Vertex> N_a;
 Vector<Vertex> N_bi;
 Vector<Vertex> N_a_U_N_d;
 Vector<Vertex> outgoingNeighborsOnSpanner;
 Vector<Vertex> incommingNeighborsOnSpanner;
 Vector<Vertex> kHopIncommingNeighborList;
 Vector<Vertex> kHopOutgoingNeighborList;
 Vector<Vertex> kHopN_a_U_N_d;
 Vector<Vertex> DAS;
 
 

 
 
 
 Vertex(int id, double transmissionRange, double a,double b){
	this.ID=id;
	this.transmissionRange = transmissionRange;
	this.x=a;
	this.y=b;
	this.incommingNeighborList= new Vector<Vertex>();
	this.outgoingNeighborList= new Vector<Vertex>();
	this.N_a = new Vector<Vertex>();
	this.N_d = new Vector<Vertex>();
	this.N_a_U_N_d = new Vector<Vertex>();
	this.N_bi = new Vector<Vertex>();
	this.incommingNeighborsOnSpanner=new Vector<Vertex>();
	this.outgoingNeighborsOnSpanner=new Vector<Vertex>();
	this.kHopIncommingNeighborList= new Vector<Vertex>();
	this.kHopOutgoingNeighborList=new Vector<Vertex>();
	this.kHopN_a_U_N_d = new Vector<Vertex>();
	this.visited=false;
	this.isDominatorOrAbsorbent=false;
	this.isDominatorOrAbsorbentSelected=false;
	this.DFSVisited=false;
	this.dijkstraMarker = false;
	this.WuMarker = false;
	this.timeToStart = -1;
	this.finished = false;
	this.DAS=new Vector<Vertex>();
	

	
}
 
 Vertex(int id, double transmissionRange, double a,double b, int cl){
		this.ID=id;
		this.transmissionRange = transmissionRange;
		this.x=a;
		this.y=b;
		this.classNum=cl;
		this.incommingNeighborList= new Vector<Vertex>();
		this.outgoingNeighborList= new Vector<Vertex>();
		this.N_a = new Vector<Vertex>();
		this.N_d = new Vector<Vertex>();
		this.N_a_U_N_d = new Vector<Vertex>();
		this.N_bi = new Vector<Vertex>();
		this.incommingNeighborsOnSpanner=new Vector<Vertex>();
		this.outgoingNeighborsOnSpanner=new Vector<Vertex>();
		this.kHopIncommingNeighborList= new Vector<Vertex>();
		this.kHopOutgoingNeighborList=new Vector<Vertex>();
		this.kHopN_a_U_N_d = new Vector<Vertex>();
		this.visited=false;
		this.isDominatorOrAbsorbent = false;
		this.isDominatorOrAbsorbentSelected=false;
		this.DFSVisited=false;
		this.dijkstraMarker = false;
		this.WuMarker = false;
		this.timeToStart = -1;
		this.finished = false;
		this.DAS=new Vector<Vertex>();
		
	}
 
 Vertex(){
	this.x=100;
	this.y=200;
	this.transmissionRange = 0;
	this.classNum=-1;
	this.incommingNeighborList= new Vector<Vertex>();
	this.outgoingNeighborList= new Vector<Vertex>();
	this.N_a = new Vector<Vertex>();
	this.N_d = new Vector<Vertex>();
	this.N_a_U_N_d = new Vector<Vertex>();
	this.N_bi = new Vector<Vertex>();
	this.incommingNeighborsOnSpanner=new Vector<Vertex>();
	this.outgoingNeighborsOnSpanner=new Vector<Vertex>();
	this.kHopIncommingNeighborList= new Vector<Vertex>();
	this.kHopOutgoingNeighborList=new Vector<Vertex>();
	this.kHopN_a_U_N_d = new Vector<Vertex>();
	this.visited=false;
	this.isDominatorOrAbsorbent=false;
	this.isDominatorOrAbsorbentSelected=false;
	this.DFSVisited=false;
	this.dijkstraMarker = false;
	this.WuMarker = false;
	this.timeToStart = -1;
	this.finished = false;
	this.DAS=new Vector<Vertex>();
		
 }
 
 //************************************ Getters & Setters *********************************
 public void setClassNum(int cl)
 {
	 this.classNum=cl;
 }
 public int getClassNum(){
	 return this.classNum;
 }
 public void setXandY(double x, double y)
 {
	 this.x=x;
	 this.y=y;
	 
 }
 public int getID()
 {
	 return this.ID;
 }
 public double getX()
 {
	 return this.x;
 }
 public double getY()
 {
	 return this.y;
 }
 public double getTransmissionRange()
 {
	 return this.transmissionRange;
 }
 public void setTransmissionRange(double range)
 {
	 this.transmissionRange = range;
 }
 public Vector<Vertex> getIncommingNeighbors(){
	 return this.incommingNeighborList;
 }
 public Vector<Vertex> getOutgoingNeighbors(){
	 return this.outgoingNeighborList;
 }
 public Vector<Vertex> getN_a()
 {
	 return this.N_a;
 }
 public Vector<Vertex> getN_d()
 {
	 return this.N_d;
 }
 public Vector<Vertex> getN_bi()
 {
	 return this.N_bi;
 }
 public Vector<Vertex> getN_a_U_N_d()
 {
	 return this.N_a_U_N_d;
 }
 public void addToN_a(Vertex v)
 {
	 this.N_a.add(v);
 }
 public void addToN_d(Vertex v)
 {
	 this.N_d.add(v);
 }
 public void addToN_bi(Vertex v)
 {
	 this.N_bi.add(v);
 }
 public void addToN_a_U_N_d(Vertex v)
 {
	 this.N_a_U_N_d.add(v);
 }
 

 public boolean isVisited(){
	 return this.visited;
 }
 
 public void addIncommingNeighbor(Vertex v){
	 this.incommingNeighborList.add(v);
	 
 }
 public void addOutgoingNeighbor(Vertex v){
	 this.outgoingNeighborList.add(v);
	 
 }
 public boolean getDFSMarker()
 {
	 return this.DFSVisited;
 }

 public boolean isDominatorOrAbsorbent(){
	 return this.isDominatorOrAbsorbent;
 }
 public void markAsDASNode(){
	 this.isDominatorOrAbsorbent=true;
 }

 public void removeFromDAS(){
	 this.isDominatorOrAbsorbent=false;
 }

 public Vector<Vertex> getDASInCell()
 {
	 return this.DAS;
 }
 

 public void setDijkstraMarker()
 {
	 this.dijkstraMarker = true;
 }
 public void clearDijkstraMarker()
 {
	 this.dijkstraMarker = false;
 }
 public boolean getDijkstraMarker()
 {
	 return this.dijkstraMarker;
 } 
 
 public void addToDAS(Vertex v){
	 this.DAS.add(v);
 }
 
 public void setDAS(Vector<Vertex> s)
 {
	this.DAS=s;
 }

 public void markVisited(){
	 this.visited=true;
 }
 public void unmarkVisited(){
	 this.visited=false;
 }
 public void markDFSVisited()
 {
	 this.DFSVisited=true;
 }
 public void clearDFSVisited()
 {
	 this.DFSVisited=false;
 }
 public boolean isDFSVisited(){
	 return this.DFSVisited;
 }
 
 public int getUnidirectionalDegree()
 {
	 return this.getN_d().size()+this.getN_a().size();
 }
 public int getNumOfIncomingGrayNeighbors()
 {
	 int num=0;
	 for (Vertex v:this.getN_d())
		 if (v.isGray())
			 ++ num;
	 return num;
 }
 //********************************** Wu's algorithm for Unidirectional graphs ***********
 public boolean getWuMarker()
 {
	 return this.WuMarker;
 }
 public void setWuMarker()
 {
	 this.WuMarker = true;
 }
 public void resetWuMarker()
 {
	 this.WuMarker = false;
 }
 
public Vector<Vertex> calculateKHopN_a_U_N_d(int k){
	 
	 Vector<Vertex> vertexSet= new Vector<Vertex>();
	 Vector<Vertex> currentLevel= new Vector<Vertex>();
	 Vector<Vertex> nextLevel= new Vector<Vertex>();
	 currentLevel.add(this);
	 vertexSet.add(this);
	 this.markVisited();
	 for (int i=0;i<k; i++)
	 {
		 for (Vertex v: currentLevel)
		 {
			 for (Vertex u:v.getN_a())
			 {
				 if (!u.isVisited())
				 {
					 u.markVisited();
					 nextLevel.add(u);
					 vertexSet.add(u);
				 }
				 
			 }
			 
		 }
		 currentLevel.clear();
		 for (Vertex u: nextLevel)
			 currentLevel.add(u);
		 nextLevel.clear();
	 }
	 for (Vertex v:vertexSet)
		 v.unmarkVisited();
	 currentLevel.clear();
	 nextLevel.clear();
	 //repeat the same procedure for the N(d) neighbor set
	 currentLevel.add(this);
	 vertexSet.add(this);
	 this.markVisited();
	 for (int i=0;i<k; i++)
	 {
		 for (Vertex v: currentLevel)
		 {
			 for (Vertex u:v.getN_d())
			 {
				 if (!u.isVisited())
				 {
					 u.markVisited();
					 nextLevel.add(u);
					 vertexSet.add(u);
				 }
				 
			 }
			 
		 }
		 currentLevel.clear();
		 for (Vertex u: nextLevel)
			 currentLevel.add(u);
		 nextLevel.clear();
	 }
	 for (Vertex v:vertexSet)
		 v.unmarkVisited();
	 
	
	 return vertexSet;
	 
 }
public Vector<Vertex> setKHopN_a_U_N_d(int k)
{
	 this.kHopN_a_U_N_d=this.calculateKHopN_a_U_N_d(k);
	 return this.kHopN_a_U_N_d;
}
 
 //*****************************************************************************************
//**************************** DominatingAbsorbentSpanningTree *****************************
private int color; // white,gray, black, blue
private Vertex parent;

public int getColor(){
	 return this.color;
}
public void unColor()
{
	this.color=Constants.Uncolored;
}
public void markGray(){
	 this.color=Constants.GRAY;
}
public void markBlack(){
	 this.color=Constants.BLACK;
}
public void markOrange(){
	 this.color=Constants.ORANGE;
}
public void markWhite(){
	 this.color=Constants.WHITE;
}
public void markBlue(){
	 this.color=Constants.BLUE;
}

public boolean isOrange(){
	 return (this.color==Constants.ORANGE);
}
public boolean isWhite(){
	 return (this.color==Constants.WHITE);
}
public boolean isGray(){
	 return (this.color==Constants.GRAY);
}
public boolean isBlack(){
	 return (this.color==Constants.BLACK);
}
public boolean isBlue(){
	 return (this.color==Constants.BLUE);
}
public boolean isUnColored()
{
	return (this.color == Constants.Uncolored);
}
public Vertex getParent()
{
	return this.parent;
}
public void setParent(Vertex parent)
{
	this.parent = parent;
}
//************************ End of DominatingAbsorbentSpanningTree **************************
 public Vector<Vertex> calculateKHopOutgoingNeighbors(int k){
	 
	 Vector<Vertex> vertexSet= new Vector<Vertex>();
	 Vector<Vertex> currentLevel= new Vector<Vertex>();
	 Vector<Vertex> nextLevel= new Vector<Vertex>();
	 currentLevel.add(this);
	 vertexSet.add(this);
	 this.markVisited();
	 for (int i=0;i<k; i++)
	 {
		 for (Vertex v: currentLevel)
		 {
			 for (Vertex u:v.getOutgoingNeighbors())
			 {
				 if (!u.isVisited())
				 {
					 u.markVisited();
					 nextLevel.add(u);
					 vertexSet.add(u);
				 }
				 
			 }
			 
		 }
		 currentLevel.clear();
		 for (Vertex u: nextLevel)
			 currentLevel.add(u);
		 nextLevel.clear();
	 }
	 for (Vertex v:vertexSet)
		 v.unmarkVisited();
	 
	
	 return vertexSet;
	 
 }
 
 public Vector<Vertex> calculateKHopIncommingNeighbors(int k){
	 
	 Vector<Vertex> vertexSet= new Vector<Vertex>();
	 Vector<Vertex> currentLevel= new Vector<Vertex>();
	 Vector<Vertex> nextLevel= new Vector<Vertex>();
	 currentLevel.add(this);
	 vertexSet.add(this);
	 this.markVisited();
	 for (int i=0;i<k; i++)
	 {
		 for (Vertex v: currentLevel)
		 {
			 for (Vertex u:v.getIncommingNeighbors())
			 {
				 if (!u.isVisited())
				 {
					 u.markVisited();
					 nextLevel.add(u);
					 vertexSet.add(u);
				 }
				 
			 }
			 
		 }
		 currentLevel.clear();
		 for (Vertex u: nextLevel)
			 currentLevel.add(u);
		 nextLevel.clear();
	 }
	 for (Vertex v:vertexSet)
		 v.unmarkVisited();
	 
	
	 return vertexSet;
	 
 }
 public Vertex getMinDegreeVertexInDominatingNeighbors ()
 {
	 Vertex min= this;
	 for (Vertex v: this.getIncommingNeighbors())
	 {
		 if (v.isDominatorOrAbsorbent)
			 if (v.getOutgoingNeighbors().size()<min.getOutgoingNeighbors().size())
				 min= v;
			 else if ((v.getOutgoingNeighbors().size()== min.getOutgoingNeighbors().size())&&(v.getID() < min.getID()))
				 min=v;
	 }
	 return min;
 }
 
 public Vertex getMinDegreeVertexInAbsorbentNeighbors ()
 {
	 Vertex min= this;
	 for (Vertex v: this.getOutgoingNeighbors())
	 {
		 if (v.isDominatorOrAbsorbent)
			 if (v.getIncommingNeighbors().size()<min.getIncommingNeighbors().size())
				 min= v;
			 else if ((v.getIncommingNeighbors().size()== min.getIncommingNeighbors().size())&&(v.getID() < min.getID()))
				 min=v;
	 }
	 return min;
 }
 
 public boolean doesIntersect(Set<Vertex> s1,Set<Vertex> s2)
 {
	 for(Vertex u:s1)
		 for (Vertex v:s2)
			 if (u.getID()==v.getID())
				 return true;
	 return false;
		 
 }
	

 
 public Set<Vertex> getMyDominators(){
	 Set<Vertex> result= new HashSet<Vertex>();
	 if (this.isDominatorOrAbsorbent())
		 result.add(this);
	 for (Vertex v:this.getIncommingNeighbors())
		 if (v.isDominatorOrAbsorbent())
			 result.add(v);
	 return result;
			 
 }
 
 public Set<Vertex> getMyAbsorbents(){
	 Set<Vertex> result= new HashSet<Vertex>();
	 if (this.isDominatorOrAbsorbent())
		 result.add(this);
	 for (Vertex v:this.getOutgoingNeighbors())
		 if (v.isDominatorOrAbsorbent())
			 result.add(v);
	 return result;
			 
 }
 /*public Set<Vertex> getMyWuLiDominators()
 {
	 Set<Vertex> myWuLiDominators = new HashSet<Vertex>();
	 if (this.getMarker())
		 myWuLiDominators.add(this);
	 for (Vertex v:this.getNeighbors())
		 if (v.getMarker())
			 myWuLiDominators.add(v);
	 return (myWuLiDominators);
 }
 */
 
 public void setKHopIncommingNeighborsList(int k)
 {
	 this.kHopIncommingNeighborList=this.calculateKHopIncommingNeighbors(k);
 }
 
 public void setKHopOutgoingNeighborsList(int k)
 {
	 this.kHopOutgoingNeighborList=this.calculateKHopOutgoingNeighbors(k);
 }

 public Vector<Vertex> getKHopIncommingNeighborsList()
 {
	 return this.kHopIncommingNeighborList;
 }
 
 public Vector<Vertex> getKHopOutgoingNeighborsList()
 {
	 return this.kHopOutgoingNeighborList;
 }
 public boolean isDominatorOrAbsorbentSelected()
 {
	 return this.isDominatorOrAbsorbentSelected;
 }

 public void markAsDASelectionDoneInCell(){
	 this.isDominatorOrAbsorbentSelected=true;
 }

 
 public void clearTags(){
	 this.isDominatorOrAbsorbentSelected=false;
	 this.isDominatorOrAbsorbent=false;
	 this.DAS=new Vector<Vertex>();
	 this.visited= false;
	 this.DFSVisited=false;
	 
	 
 }
 
 
 
 public int getNumOfDominatingNeighborsInDAS()
 {
	 int result=0;
	 for (Vertex v:this.getIncommingNeighbors())
	 {
		 if (v.isDominatorOrAbsorbent())
			 result++;
	 }
	 return result;
 }
 
 public int getNumOfAbsorbentNeighborsInDAS()
 {
	 int result=0;
	 for (Vertex v:this.getOutgoingNeighbors())
	 {
		 if (v.isDominatorOrAbsorbent())
			 result++;
	 }
	 return result;
 }
/* public int getNumOfNeighborsInWuLiCDS()
 {
	 int num=0;
	 for (Vertex v:this.getNeighbors())
		 if (v.getMarker())
			 ++ num;
	 return num;
 }*/
 public Vector<Vertex> getNeighborsInDAS()
 {
	 Vector<Vertex> result=new Vector<Vertex>();
	 for (Vertex v:this.getIncommingNeighbors())
	 {
		 if (v.isDominatorOrAbsorbent())
			 result.add(v);
	 }
	 for (Vertex v:this.getOutgoingNeighbors())
	 {
		 if (v.isDominatorOrAbsorbent())
			 result.add(v);
	 }
	 return result;
 }

 public Vector<Vertex> getKHopNeighborsInDAS(int k)
 {
	 Vector<Vertex> result=new Vector<Vertex>();
	 for (Vertex v:this.calculateKHopIncommingNeighbors(k))
	 {
		 if (v.getID() != this.getID())
		 if (v.isDominatorOrAbsorbent())
			 result.add(v);
	 }
	 for (Vertex v:this.calculateKHopOutgoingNeighbors(k))
	 {
		 if (v.getID() != this.getID())
		 if (v.isDominatorOrAbsorbent())
			 result.add(v);
	 }
	 return result;
 }
 

 
 /*public Vertex getMaxDegreeDominatorInKHopNeighborsBluePinkDS(int k)
 {
	 Vertex maxDegreeVertex = new Vertex();
	 for (Vertex v: this.calculateKHopIncommingNeighbors(k))
	 {
		 if (v.getID() != this.getID())
		 if ((v.isDominatorOrAbsorbent())&& v.getOutgoingNeighbors().size()> this.getOutgoingNeighbors().size())
			 maxDegreeVertex = v;
	 }
	 for (Vertex v: this.calculateKHopOutgoingNeighbors(k))
	 {
		 if (v.getID() != this.getID())
		 if ((v.isDominatorOrAbsorbent())&& v.getOutgoingNeighbors().size()> this.getOutgoingNeighbors().size())
			 maxDegreeVertex = v;
	 }
	 
	 return maxDegreeVertex;
 }*/
 
/* public Vertex getMaxDegreeDominatorInKHopNeighborsBluePinkAS(int k)
 {
	 Vertex maxDegreeVertex = new Vertex();
	 for (Vertex v: this.calculateKHopIncommingNeighbors(k))
	 {
		 if (v.getID() != this.getID())
		 if ((v.isDominatorOrAbsorbent())&& v.getIncommingNeighbors().size()> this.getIncommingNeighbors().size())
			 maxDegreeVertex = v;
	 }
	 for (Vertex v: this.calculateKHopOutgoingNeighbors(k))
	 {
		 if (v.getID() != this.getID())
		 if ((v.isDominatorOrAbsorbent())&& v.getIncommingNeighbors().size()> this.getIncommingNeighbors().size())
			 maxDegreeVertex = v;
	 }
	 
	 return maxDegreeVertex;
 }*/
/* public Vector<Vertex> getNeighborsInWuLiCDS()
 {
	 Vector<Vertex> neighborsInWuLiCDS = new Vector<Vertex>();
	 
	 for (Vertex v: this.getNeighbors())
		 if (v.getMarker())
			 neighborsInWuLiCDS.add(v);
	 
	 return neighborsInWuLiCDS;
 }*/
 
 
 public Set<Vertex> subtractFromSet(Set<Vertex> s)
 {
     Set<Vertex> result= new HashSet<Vertex>();
     for (Vertex v:s)
     {
        if (v.getID()!=this.getID())
             result.add(v);
     }
     return result;
 }
 
 
 public boolean isInList(Vector<Vertex> vl)
 {
	 boolean found=false;
	 for (int i=0; i<vl.size(); i++){
		 if (this.getID()==vl.elementAt(i).getID())
			 {found=true;
			  break;
			 }
		 
	 }
	return found;	 
 }
 public boolean isInList(Vertex v,TreeSet<Edge> edges)
 {
 	 boolean found=false;
 	 for (Edge edge : edges)
 		 if (((edge.getVertexA().getID()==this.getID())&&    (edge.getVertexB().getID()==v.getID()))||    ((edge.getVertexA().getID()==v.getID())&&    (edge.getVertexB().getID()==this.getID())))
 		 {	 
 			 found=true;
 		 	 break;
 		 }
 	 return found;
 	 
 }
 
 
 public double EuclideanDistance(Vertex v){
	 
	  return Math.sqrt((v.x-this.x)*(v.x-this.x)+(v.y-this.y)*(v.y-this.y));
	 }
 @Override
 public String toString()
 {
     String s=  "( ID: " + this.ID + ", X: " + this.x + ", Y: "+ this.y+", ClassNum: "+this.getClassNum()+ ")";
     s=s +"\n incomming neighbor list: ";
     for (Vertex v:this.getIncommingNeighbors())
    	 s=s+", ("+v.getID()+", "+v.getClassNum()+")";
     s=s +"\n outgoing neighbor list: ";
     for (Vertex v:this.getOutgoingNeighbors())
    	 s=s+", ("+v.getID()+", "+v.getClassNum()+")";
     s=s+"\n";
     return s;
     
 }
 
 Vertex(double xC, double yC) {
		this.x = xC;
		this.y = yC;
	}
 
//*********************************** methods used for WuLi's algo ****************************
 //We could've used the "containsAll" method of the class Vector instead !!!
 
//This method does not consider equality of sets as subset relationship
 public boolean incommingNeighborSetIsASubsetOf(Vertex v)
 {
     
     if (this.incommingNeighborList.size()>= v.incommingNeighborList.size())
         return false;
     
     else
     {
    	 for (Vertex w:this.getIncommingNeighbors())
    	 {
    		 boolean found=false;
    		 for (Vertex u:v.getIncommingNeighbors())
    			 if (w.getID()==u.getID())
    			 {
    				 found=true;
    				 break;
    			 }
    		 if (!found)
    			 return false;
    	 }
     }
     return true;
         
 }
 
 public boolean outgoingNeighborSetIsASubsetOf(Vertex v)
 {
     
     if (this.outgoingNeighborList.size()>= v.outgoingNeighborList.size())
         return false;
     
     else
     {
    	 for (Vertex w:this.getOutgoingNeighbors())
    	 {
    		 boolean found=false;
    		 for (Vertex u:v.getOutgoingNeighbors())
    			 if (w.getID()==u.getID())
    			 {
    				 found=true;
    				 break;
    			 }
    		 if (!found)
    			 return false;
    	 }
     }
     return true;
         
 }
 //This method checks for equality of sets
 public boolean incommingneighborSetIsEqualTo(Vertex v)
 {
	 if (this.incommingNeighborList.size()== v.incommingNeighborList.size())
     {
    	 for (Vertex w:this.getIncommingNeighbors())
    	 {
    		 boolean found=false;
    		 for (Vertex u:v.getIncommingNeighbors())
    			 if (w.getID()==u.getID())
    			 {
    				 found=true;
    				 break;
    			 }
    		 if (!found)
    			 return false;
    	 }
    	 return true;
     }
     return false;
 }
 public boolean outgoingneighborSetIsEqualTo(Vertex v)
 {
	 if (this.outgoingNeighborList.size()== v.outgoingNeighborList.size())
     {
    	 for (Vertex w:this.getOutgoingNeighbors())
    	 {
    		 boolean found=false;
    		 for (Vertex u:v.getOutgoingNeighbors())
    			 if (w.getID()==u.getID())
    			 {
    				 found=true;
    				 break;
    			 }
    		 if (!found)
    			 return false;
    	 }
    	 return true;
     }
     return false;
 }
 /*public boolean incommingNeighborSetIsASubsetOf(Vertex u, Vertex v)
 {
	 for (Vertex w : this.getIncommingNeighbors()) {
			boolean found = false;
			for (Vertex y : v.getNeighbors())
				if (w.getID() == y.getID()) {
					found = true;
					break;
				}
			if (!found)
				for (Vertex z : u.getNeighbors())
					if (w.getID() == z.getID()) {
						found = true;
						break;
					}
			if (!found)
				return false;
		}
	    
	     return true;
 }
 public boolean neighborSetIsASubsetOf(Vertex u, Vertex v)
 {
	 for (Vertex w : this.getNeighbors()) {
			boolean found = false;
			for (Vertex y : v.getNeighbors())
				if (w.getID() == y.getID()) {
					found = true;
					break;
				}
			if (!found)
				for (Vertex z : u.getNeighbors())
					if (w.getID() == z.getID()) {
						found = true;
						break;
					}
			if (!found)
				return false;
		}
	    
	     return true;
 }*/
 //****************************************************************************************
 //**************************** Unidirectional PInOut **********************************
 //****************************************************************************************
 private int status;
 private boolean finished;
 private int timeToStart;
 
 public void markIn()
 {
	 this.status = Constants.IN;
 }
 public void markOut()
 {
	 this.status = Constants.OUT;
 }
 public int getStatus()
 {
	 return this.status;	 
 }
 
 public boolean canStart(){
	    //return true if you have the lowest rank among nonfinished neighbors

	    for (Vertex v:this.getOutgoingNeighbors())
	            if (!v.isFinished()){
	                    if (v.getNumOfNeighborsInPInOutCDAS() <this.getNumOfNeighborsInPInOutCDAS())
	                            return false;
	                    else if (v.getNumOfNeighborsInPInOutCDAS() == this.getNumOfNeighborsInPInOutCDAS())
	                            if (v.getID() <this.getID())
	                                    return false;
	            }
	    return true;


	}
 
 public boolean canStart(int k){
	    //return true if you have the lowest rank among nonfinished neighbors

	    for (Vertex v:this.getKHopNeighborsInPInOutCDAS(k))
	            if (!v.isFinished()&& (v.getID()!=this.getID())){
	                    if (v.getNumOfNeighborsInPInOutCDAS() <this.getNumOfNeighborsInPInOutCDAS())
	                            return false;
	                    else if (v.getNumOfNeighborsInPInOutCDAS() == this.getNumOfNeighborsInPInOutCDAS())
	                            if (v.getID() <this.getID())
	                                    return false;
	            }
	    return true;


	}
 
 public Vector<Vertex> getPInOutDominators()
 {
	 Vector<Vertex> dominators = new Vector<Vertex>();
	 if (this.getStatus() == Constants.IN)
		 dominators.add(this);
	 for (Vertex v:this.getN_d())
		 if (v.getStatus() == Constants.IN)
			 dominators.add(v);
	 return dominators;
 }
 public Vector<Vertex> getPInOutAbsorbents()
 {
	 Vector<Vertex> absorbents = new Vector<Vertex>();
	 if (this.getStatus() == Constants.IN)
		 absorbents.add(this);
	 for (Vertex v:this.getN_a())
		 if (v.getStatus() == Constants.IN)
			 absorbents.add(v);
	 return absorbents;
 }
 
public Vector<Vertex> calculateKHopN_a_d(int k){
	 
	 Vector<Vertex> vertexSet= new Vector<Vertex>();
	 Vector<Vertex> currentLevel= new Vector<Vertex>();
	 Vector<Vertex> nextLevel= new Vector<Vertex>();
	 currentLevel.add(this);
	 vertexSet.add(this);
	 this.markVisited();
	 for (int i=0;i<k; i++)
	 {
		 for (Vertex v: currentLevel)
		 {
			 for (Vertex u:v.getN_a_U_N_d())
			 {
				 if (!u.isVisited())
				 {
					 u.markVisited();
					 nextLevel.add(u);
					 vertexSet.add(u);
				 }				 
			 }			 
		 }
		 currentLevel.clear();
		 for (Vertex u: nextLevel)
			 currentLevel.add(u);
		 nextLevel.clear();
	 }
	 for (Vertex v:vertexSet)
		 v.unmarkVisited();
	 
	return vertexSet;
 }

public int getNumOfNeighborsInPInOutCDAS()
{
	int num=0;
	for (Vertex v:this.getN_a_U_N_d())
		if (v.getStatus() == Constants.IN)
			++ num;
	return num;
}
public Vector<Vertex> getNeighborsInCDAS(){
	Vector<Vertex> neighborsInCDAS = new Vector<Vertex>();
	for (Vertex v:this.getN_a_U_N_d())
		if (v.getStatus() == Constants.IN)
			neighborsInCDAS.add(v);
	return neighborsInCDAS;
}

public Vector<Vertex> getKHopNeighborsInPInOutCDAS(int k)
{
	Vector<Vertex> neighborsInCDAS  = new Vector<Vertex>();
	for (Vertex v:this.calculateKHopN_a_d(k))
		if (v.getID() != this.getID())
			if (v.getStatus() == Constants.IN)
				neighborsInCDAS.add(v);
	return neighborsInCDAS;
}
public Vector<Vertex> subtractFromSet(Vector<Vertex> s)
{
    Vector<Vertex> result= new Vector<Vertex>();
    for (Vertex v:s)
    {
       if (v.getID()!=this.getID())
            result.add(v);
    }
    return result;
}

public int getTimeToStart()
{
	return this.timeToStart;
}

public void decrementTimeToStart()
{
	-- this.timeToStart;
}

public void markAsFinished ()
{
	this.finished = true;
}

public boolean isFinished()
{
	return finished;
}
public void restart(){
	this.finished = false;
}

public void setTimeToStartKHop(int kHopNeighborhood)
{
	int tts=0;
	Vector<Vertex> kHopNeighbors = new Vector<Vertex>();
	kHopNeighbors = this.getKHopNeighborsInPInOutCDAS(kHopNeighborhood);
	for (Vertex v:kHopNeighbors)
		if (!v.isFinished()) {
				if (v.getN_a_U_N_d().size() < this.getN_a_U_N_d().size())
					++tts;
				else if (v.getN_a_U_N_d().size() == this.getN_a_U_N_d().size())
					if (v.getID() < this.getID())
						++tts;
			}
	this.timeToStart = tts;
		
}


public int outgoingDegreeToHexagon(int i)
{
	 int d=0;
	 for (Iterator<Vertex> it=this.outgoingNeighborList.iterator(); it.hasNext();)
	 {
		 Vertex v= it.next();
		 if (v.getClassNum()==i)
			 d++;
	 }
	 return d;
	 
}
public Vertex outgoingNeighborWithMaxDegreeIn(int classnum){
	Vertex candidate=null;
	for (Vertex v:this.outgoingNeighborList)
		if (v.getClassNum()==classnum)
			candidate=v;
	for (Vertex v:this.outgoingNeighborList)
		if ((v.getClassNum()==classnum)&&(v.getIncommingNeighbors().size()>candidate.getIncommingNeighbors().size()))
			candidate=v;
	return candidate;
}

public int incommingDegreeFromHexagon(int i)
{
	 int d=0;
	 for (Iterator<Vertex> it=this.incommingNeighborList.iterator(); it.hasNext();)
	 {
		 Vertex v= it.next();
		 if (v.getClassNum()==i)
			 d++;
	 }
	 return d;
	 
}

public Vertex incommingNeighborWithMaxDegreeIn(int cl){
	
	Vertex candidate=null;
	for (Vertex v:this.incommingNeighborList)
		if (v.getClassNum()==cl){
			candidate=v;
			break;
			}
	
	for (Vertex v:this.incommingNeighborList)
		if ((v.getClassNum()==cl)&&(v.getOutgoingNeighbors().size()>candidate.getOutgoingNeighbors().size()))
			candidate=v;
	
	return candidate;
}

public boolean isThereAnIncommingEdgeFromDASIn(int hexagonNumber){
	for (Vertex v: this.getDASInCell())
		for(Vertex u: v.getIncommingNeighbors())
			if (u.getClassNum()==hexagonNumber)
				if (u.isInList(u.getDASInCell()))
					return true;
	
	return false;
}

public boolean isThereAnOutgoingEdgeToDASIn(int hexagonNumber){
	for (Vertex v: this.getDASInCell())
		for(Vertex u: v.getOutgoingNeighbors())
			if (u.getClassNum()==hexagonNumber)
				if (u.isInList(u.getDASInCell()))
					return true;
	
	return false;
}

public boolean isThereAVertexWithAnIncommingEdgeFromDASIn(Vertex u){
	for (Vertex v: u.getDASInCell())
		for(Vertex w: v.getOutgoingNeighbors())
			if (w.getClassNum()==this.classNum)
					return true;
	
	return false;
}
public boolean isThereAVertexWithAnOutgoingEdgeToDASIn(Vertex u){
	for (Vertex v: u.getDASInCell())
		for(Vertex w: v.getIncommingNeighbors())
			if (w.getClassNum()==this.classNum)
					return true;
	
	return false;
}

public Vertex MaxDegreeVertexWithAnOutgoingEdgeToDASIn(Vertex u){
	Vertex candidate=null;
	for (Vertex v: u.getDASInCell())
		for(Vertex w: v.getIncommingNeighbors())
			if (w.getClassNum()==this.classNum){
					candidate=w;
					break;
			}
	for (Vertex v: u.getDASInCell())
		for(Vertex w: v.getIncommingNeighbors())
			if (w.getClassNum()==this.classNum)
				if (w.getOutgoingNeighbors().size()>candidate.getOutgoingNeighbors().size())	
					candidate=w;
					
			
	
	
	return candidate;
}

public Vertex MaxDegreeVertexWithAnIncommingEdgeFromDASIn(Vertex u){
	Vertex candidate=null;
	for (Vertex v: u.getDASInCell())
		for(Vertex w: v.getOutgoingNeighbors())
			if (w.getClassNum()==this.classNum){
					candidate=w;
					break;
			}
	for (Vertex v: u.getDASInCell())
		for(Vertex w: v.getOutgoingNeighbors())
			if (w.getClassNum()==this.classNum)
				if (w.getIncommingNeighbors().size()>candidate.getIncommingNeighbors().size())	
					candidate=w;
					
			
	
	
	return candidate;
}

public Vertex MaxDegreeVertexWithAnIncommingEdgeFrom(Vertex u){
	Vertex candidate=null;
	Vector<Vertex> uHexagon=new Vector<Vertex>();
	uHexagon.add(u);
	for (Vertex v: u.getIncommingNeighbors())
		if (v.getClassNum()==u.getClassNum())
			uHexagon.add(v);
	for (Vertex v: uHexagon)
		for(Vertex w: v.getOutgoingNeighbors())
			if (w.getClassNum()==this.classNum){
					candidate=w;
					break;
			}
	for (Vertex v: uHexagon)
		for(Vertex w: v.getOutgoingNeighbors())
			if (w.getClassNum()==this.classNum)
				if (w.getIncommingNeighbors().size()>candidate.getIncommingNeighbors().size())	
					candidate=w;
					
			
	
	
	return candidate;
}
public Vertex MaxDegreeVertexWithAnOutgoingEdgeTo(Vertex u){
	Vertex candidate=null;
	Vector<Vertex> uHexagon=new Vector<Vertex>();
	uHexagon.add(u);
	for (Vertex v: u.getIncommingNeighbors())
		if (v.getClassNum()==u.getClassNum())
			uHexagon.add(v);
	for (Vertex v: uHexagon)
		for(Vertex w: v.getIncommingNeighbors())
			if (w.getClassNum()==this.classNum){
					candidate=w;
					break;
			}
	for (Vertex v: uHexagon)
		for(Vertex w: v.getIncommingNeighbors())
			if (w.getClassNum()==this.classNum)
				if (w.getOutgoingNeighbors().size()>candidate.getOutgoingNeighbors().size())	
					candidate=w;
					
			
	
	
	return candidate;
}


public void updateHexagon(){
	 for (Vertex v:this.incommingNeighborList)
	 {
		 if (v.getClassNum()==this.getClassNum())
				v.addToDAS(this);
	 }
	 for (Vertex v:this.outgoingNeighborList)
	 {
		 if (v.getClassNum()==this.getClassNum())
				v.addToDAS(this);
	 }
	 this.markAsDASNode();
	 this.addToDAS(this);
}
/*public Vertex VertexWithMaxOutgoingDegreeInHexagon(){
	Vertex candidate= this;
	//for all nodes in hexagon ; note that nodes in hexagon are bidirectional
	for (Vertex v:this.outgoingNeighborList){
		if (v.getClassNum()==this.classNum)
		if (v.getOutgoingNeighbors().size()>candidate.getOutgoingNeighbors().size())
			candidate=v;
	}
	return candidate;
}
public Vertex VertexWithMaxIncommingDegreeInHexagon(){
	Vertex candidate= this;
//	System.out.println("VertexWithMaxIncommingDegreeInHexagon is called for"+this);
	//for all nodes in hexagon ; note that nodes in hexagon are bidirectional
	for (Vertex v:this.outgoingNeighborList){
		if (v.getClassNum()==this.classNum)
		if (v.getIncommingNeighbors().size()>candidate.getIncommingNeighbors().size())
			candidate=v;
	}
	return candidate;
}*/

}
