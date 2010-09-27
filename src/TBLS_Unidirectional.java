import java.awt.geom.CubicCurve2D;
import java.util.*;
//instead of local spanner we are connecting to all hexagons with greater class numbers
public class TBLS_Unidirectional {
	

			
	public Result heuristic(Graph g, int pruningLocality){
	//	public Result heuristic(Graph g, TreeSet<Edge> spannerEdges, int pruningLocality){
			System.out.println("running heuristic : max degree optimized on directed graphs ...");
			for (Vertex v:g.getVertices())
				v.markOut();
			double r = (double)g.getMinTransmissionRange()/g.getMaxTransmissionRange();
			int k= 1+ (int)Math.ceil(4.0/(Math.sqrt(3)*r));
			
			System.out.println("Heuristic: r is "+r +" and k is "+k);
			int numOfCells = k*k;
			Vector <Vertex> SCDAS= new Vector<Vertex>();
			List<Vector<Vertex>> list = new ArrayList<Vector<Vertex>>();
			for (int i=0; i<numOfCells; i++)
				list.add(new Vector<Vertex>());
			Vertex vertex0=new Vertex();
			System.out.println("Heuristic: Num of cells is  "+numOfCells);
			for (Vertex v: g.getVertices())
			{
				if (v.getID()==0)
					vertex0=v;
				if (v.getClassNum()==0)
		 		 System.out.println("Error :  Vertex "+ v.getID()+"has class number "+ v.getClassNum());
				Vector<Vertex> temp =list.get(v.getClassNum()-1);
				temp.addElement(v);
				list.set(v.getClassNum()-1, temp);
			}
			
			for (int i=0; i<numOfCells; i++)
			{
				
				//check every node with class number i
				for (Vertex u:list.get(i))
				{
					/*if (i==15)
						System.out.println("Vertex "+u.getID()+" ClassNum: "+u.getClassNum());*/
					
					if (!u.isDominatorOrAbsorbentSelected())
					{
					
						Set <Integer> dominatingHexagons= new HashSet<Integer>();
						Set<Integer> absorbingHexagons=new HashSet<Integer>();
					//	System.out.println("Originally u is "+u);
						Vector <Vertex> hexagon = new Vector<Vertex>();
						hexagon.addElement(u);
						//within the hexagon all links are bidirectional
						for (Vertex v: u.getIncommingNeighbors())
						{
							if (v.getClassNum()==u.getClassNum())
								hexagon.addElement(v);
						}						
						
						for (Vertex v: hexagon)
						{
							for (Vertex w: v.getOutgoingNeighbors())
							{
								if (w.getClassNum()> v.getClassNum())
									dominatingHexagons.add(w.getClassNum());	
								
							}
							
							for (Vertex w: v.getIncommingNeighbors())
							{
								if (w.getClassNum()> v.getClassNum())
									absorbingHexagons.add(w.getClassNum());	
							}
							
						}
						/*System.out.println("When calculating CDs in hexagon "+u.getClassNum());
						System.out.println("Absorbing hexagons are: ");
						System.out.println(absorbingHexagons);
						
						System.out.println("Dominating hexagons are: ");
						System.out.println(dominatingHexagons);*/
						
						// "s" contains the set of numbers of hexagons, which we should cover
						
						//Vector<Vertex> localDAS = u.getDASInCell();
						
						for (int classNum:dominatingHexagons)
						{
							
							Vertex candidate=null;
							Vertex otherSide=null;
							//find a vertex in hexagon with class number equal to classNum
							Vertex vertexInDominatingHexagon =null;
							for (Vertex v:hexagon)
								for (Vertex w: v.getOutgoingNeighbors())
									if (w.getClassNum()==classNum){
										vertexInDominatingHexagon=w;
										break;
									}
							//if classNum is already covered, do nothing
							if (!u.isThereAnOutgoingEdgeToDASIn(classNum)){
								if (u.isThereAVertexWithAnOutgoingEdgeToDASIn(vertexInDominatingHexagon)){
									candidate= u.MaxDegreeVertexWithAnOutgoingEdgeToDASIn(vertexInDominatingHexagon);
									candidate.updateHexagon();
									SCDAS.add(candidate);
								}
								else{
									candidate=u.MaxDegreeVertexWithAnOutgoingEdgeTo(vertexInDominatingHexagon);
									otherSide=candidate.outgoingNeighborWithMaxDegreeIn(classNum);
									candidate.updateHexagon();
									otherSide.updateHexagon();
									SCDAS.add(candidate);
									SCDAS.add(otherSide);
								}
							}
							
						}
						for (int classNum:absorbingHexagons)
						{
							Vertex candidate=null;
							Vertex otherSide=null;
							//if classNum is already covered, do nothing
							if (!u.isThereAnIncommingEdgeFromDASIn(classNum)){
								//find a vertex in hexagon with class number equal to classNum
								Vertex vertexInAbsorbingHexagon =null;
								for (Vertex v:hexagon)
									for (Vertex w: v.getIncommingNeighbors())
										if (w.getClassNum()==classNum){
											vertexInAbsorbingHexagon=w;
											break;
										}
								
								if (u.isThereAVertexWithAnIncommingEdgeFromDASIn(vertexInAbsorbingHexagon)){
									candidate= u.MaxDegreeVertexWithAnIncommingEdgeFromDASIn(vertexInAbsorbingHexagon);
									candidate.updateHexagon();
									SCDAS.add(candidate);
								}
								else{
									/*System.out.println("Covering hexagon :"+classNum);
									System.out.println("Calculating DAS in hexagon "+hexagon.firstElement().getClassNum());*/
									candidate=u.MaxDegreeVertexWithAnIncommingEdgeFrom(vertexInAbsorbingHexagon);
								//	System.out.println("Candidate is: "+candidate);
									otherSide=candidate.incommingNeighborWithMaxDegreeIn(classNum);
									/*if (otherSide==null)
										System.out.println("Error otherside is null !!!!!!!!!");
									else
										System.out.println("otherside is "+otherSide);*/
									candidate.updateHexagon();
									otherSide.updateHexagon();
									SCDAS.add(candidate);
									SCDAS.add(otherSide);
								}
							}
						
						}
						
						for (Vertex v:hexagon)
							v.markAsDASelectionDoneInCell();
					}
				}
			}
						
			
						
						
						
					
			//***************************************************************************************************
			//******************************** Pruning round 2 *************************************************
			boolean classNumZero=false;
			for (Vertex v:SCDAS)
				if (v.getClassNum()==0)
					classNumZero=true;
			System.out.println(" Heuristic: before graph construction nodes with class num zero: "+classNumZero);
			Graph g2=new Graph(SCDAS,g.getMaxTransmissionRange(),g.getMinTransmissionRange());
			
			classNumZero=false;
			for (Vertex v:g2.getVertices())
				if (v.getClassNum()==0)
					classNumZero=true;
		//	System.out.println(" Heuristic: after graph construction nodes with class num zero: "+classNumZero);
			
			Vertex connected=g2.isStronglyConnected();
			
			if (connected.getID()!=-1){
	        	   System.out.println("Heuristic: DAS is disconnected at vertex "+connected.getID()+"( x: "+connected.getX()+", y: "+connected.getY()+", classNum: "+connected.getClassNum()+")");
	        	   System.out.println(connected.getID()+" has incomming neighbors:");
	        	   for (Vertex w:connected.getIncommingNeighbors())
	        		   System.out.println(w.getID()+"( "+w.getX()+","+w.getY()+","+w.getClassNum()+")");
	        	   System.out.println(connected.getID()+" has outgoing neighbors:");
	        	   for (Vertex w:connected.getOutgoingNeighbors())
	        		   System.out.println(w.getID()+"( "+w.getX()+","+w.getY()+","+w.getClassNum()+")");
			}
	           else
	        	   //System.out.println("DAS is connected before prunning"); 
	        //   System.out.print("Before prunning "+this.isDASCorrect(g, SCDAS));
	          
	         for (Vertex v:g.getVertices())
	        	 if (v.getClassNum()==0)
	        		 System.out.println("Heuristic: Vertex "+v.getID()+" has class number zero");
			
	         if (this.isDASCorrect(g, SCDAS)){
				PInOut_Unidirectional pu =new PInOut_Unidirectional();
				for (Vertex v:SCDAS)
					v.markIn();
				for (Vertex v:g.getVertices()){
					if (v.isInList(SCDAS))
						v.markIn();
					else
						v.markOut();
				}
				//	Vector<Vector<Vertex>> prunedCDSVector=null;
				Vector<Vector<Vertex>> prunedCDSVector = pu.PInOutUniAlgo(g, pruningLocality);
				return new Result(SCDAS, prunedCDSVector);
			}
			else{
				System.out.println("Error: Original DAS was not correct or disconnected");
			//	System.exit(0);
				return new Result(SCDAS, null);
			}
	}
	         
	
			
			
			
			/******************************************************************************************/
			
	public boolean isDASCorrect(Graph g, Vector <Vertex> DAS){
		boolean correct = true;
		//check for connectivity
		if (new Graph(DAS,g.getMaxTransmissionRange(),g.getMaxTransmissionRange()).isStronglyConnected().getID()!=-1)
			{
				correct=false;
				System.out.println("DAS is disconnected");
			
			}
		//check for correctness
		 for (Vertex v:g.getVertices())
         {
      	   if (v.isInList(DAS))
      		   v.markBlack();
      	
         }
		if (correct)
		for (Vertex v:g.getVertices()){
		
		
			if(v.getColor() != Constants.BLACK)
			{
				boolean hasADominator = false;
				boolean hasAnAbsorbent = false;
				for (Vertex u:v.getN_a())
					if (u.getColor() == Constants.BLACK)
						hasAnAbsorbent = true;
				for (Vertex u:v.getN_d())
					if (u.getColor() == Constants.BLACK)
						hasADominator = true;
				if (!hasADominator)
				{
					System.out.println("Node "+v.getID()+" has no dominator");
					System.out.println("Node "+v.getID()+" has incomming neighbors:");
					for (Vertex w:v.getIncommingNeighbors())
						System.out.println(w.getID()+" "+w.getClassNum()+" "+w.getStatus());
					correct = false;						
				}
				if (!hasAnAbsorbent)
				{
					System.out.println("Node "+v.getID()+" has no absorbent");
					System.out.println("Node "+v.getID()+" has outgoing neighbors:");
					for (Vertex w:v.getOutgoingNeighbors())
						System.out.println(w.getID()+" "+w.getClassNum()+" "+w.getStatus());
					correct = false;
				}
			}
		}
		if (correct){
			System.out.println("DAS is correct");
			return true;
		}
			
		else{
			System.out.println("ERROR: DAS is NOT correct");
			return false;
		}
			
		
	
		
	}		
		

	}



