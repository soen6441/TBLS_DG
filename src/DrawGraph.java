import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.awt.* ;
import java.awt.event.* ;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class DrawGraph extends Frame{

	 protected static class DrawArea extends Panel
	  {
	    /**
	     * Does the actual drawing.
	     * Graph (Num. of nodes, transmission range, width, length)
	     */
		
		public void zoom(Graph g, int z){
			 for (Vertex v:g.getVertices())
			 {
	 	        	v.setXandY(v.getX()*z, v.getY()*z);
	 	        	v.setTransmissionRange(v.getTransmissionRange()*z);
			 }
	 	       
		}
		
		public double calcY(double X, double R)
		{
			double Y = 0;
			Y = Math.sqrt(R*R-X*X);
			
			return Y;
		}
		
		
		public void paint(Graphics g1) {

			Vector<Vertex> CDAS = new Vector<Vertex>();
			Vector<Vertex> DT = new Vector<Vertex>();
			Vector<Vertex> AT = new Vector<Vertex>();
		//	PInOutInfo pinoutInfo = new PInOutInfo();
			int min_range=10;
			int i=0;
			try{
				Graph g;
				
		//MyFile myFile=new MyFile(".\\Graphs("+min_range+"-50)\\graphFile("+min_range+"-50)"+ i +".txt");
			  MyFile myFile=new MyFile(	".\\Graphs(10-50)\\Statistics\\error.txt");
			  FileReader input = new FileReader(myFile.getFileName());
			  BufferedReader bufRead = new BufferedReader(input); 
			/*  for (int j=0; j<15 ; j++)
			      g = myFile.generateGraphFromFile(bufRead,50,min_range);*/
			  g = myFile.generateGraphFromFile(bufRead,50,min_range);
			  
			  for (Vertex v:g.getVertices()){
				  if (v.getID()==17)
					  System.out.println("vertex 17 "+v.getClassNum()+" has incomming neighbors "+v.getIncommingNeighbors());
				  if (v.getIncommingNeighbors().size()==0)
					  System.out.println(v.getID()+" has no incomming neighbor");
					  }
			  TBLS_Unidirectional tblsu=new TBLS_Unidirectional();
			  
			//  DominatingAbsorbentSpanningTree dast = new DominatingAbsorbentSpanningTree();
			//  G_CMA g_cma = new G_CMA();
			//  Wu wu = new Wu();
		//	  PInOut_Unidirectional pinout = new PInOut_Unidirectional();
		//	  PInOut_Unidirectional_localityInfo pinoutLocalInfo= new PInOut_Unidirectional_localityInfo();			 
			 /*for (Vertex temp:g.getVertices()){
				 if (temp.getID()==7){
					 System.out.println(temp.getID()+" has class number "+temp.getClassNum());
					System.out.println("Incomming neighbors: ");
					for(Vertex v:temp.getIncommingNeighbors())
						System.out.println(v.getID()+" "+ v.getClassNum()+" Status: "+v.getStatus());
					System.out.println("Outgoing neighbors: ");
					for(Vertex v:temp.getOutgoingNeighbors())
						System.out.println(v.getID()+" "+ v.getClassNum()+" Status: "+v.getStatus());
				 }
				 }*/
			 System.out.println("***********************************"); 
			if (g.isStronglyConnected().getID() == -1) {
				System.out.println("graph is Strongly connected");
				
				System.out.println("max range: "+g.getMaxTransmissionRange()+" min range: "+g.getMinTransmissionRange());
				ClassNumber cl=new ClassNumber();
			    boolean classnumZeroFound=false;
				for (Vertex v:g.getVertices())
				 if (v.getClassNum()==0)
				 {
					 classnumZeroFound =true;
					 break;
				 }
				if (!classnumZeroFound)
					System.out.println("All class numbers calculated successfully");
				else
					System.out.println("Error calculating class numbers!!!!!!!!!!!!!!!");
				Result res= tblsu.heuristic(g, 4);
				Vector <Vertex> SCDAS=res.getCDS();
				//check how many are in
				System.out.println("CDS size before pruning: "+ res.getCDS().size() );
			//	System.out.println("CDS size after pruning: "+ res.getPrunedCDSs().size() );
		
				System.out.println("*******************************************************");
				
				for (Vertex v:SCDAS)
					v.markBlack();
				boolean correct = true;
				for (Vertex v:g.getVertices())
				{
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
					System.out.println("SCDAS is correct!!!!");
				
				
				this.zoom(g, 2);
				//************************* Draw the graph ******************************* 
				g1.setColor(Color.BLACK);
				for (Vertex v : g.getVertices())
					g1.drawString(Integer.toString(v.getID()),
							(int) v.getX() + 250, (int) v.getY() + 250);

				for (Edge edge : g.getEdges()) {
					if (edge.reverseEdge().isInListUnidirectional(g.getEdges())) {
						g1.setColor(Color.blue);
						g1.drawLine((int) edge.getVertexA().getX() + 250,
								(int) edge.getVertexA().getY() + 250,
								(int) edge.getVertexB().getX() + 250,
								(int) edge.getVertexB().getY() + 250);
					} else {
						g1.setColor(Color.gray);
						g1.drawLine((int) edge.getVertexA().getX() + 250,
								(int) edge.getVertexA().getY() + 250,
								(int) edge.getVertexB().getX() + 250,
								(int) edge.getVertexB().getY() + 250);
						}
				}
				//*************************** Draw the CDAS ********************************
				Graph cdasg = new Graph(SCDAS,g.getMaxTransmissionRange(),g.getMinTransmissionRange());
				g1.setColor(Color.MAGENTA);
				for (Edge edge : cdasg.getEdges())
					g1.drawLine((int) edge.getVertexA().getX() + 250,
							(int) edge.getVertexA().getY() + 250,
							(int) edge.getVertexB().getX() + 250,
							(int) edge.getVertexB().getY() + 250);

				//*************************** Draw the orphan node *************************
				g1.setColor(Color.blue);
				Vertex temp = cdasg.isStronglyConnected();
				if (temp.getID() != -1)
				{
					g1.fillOval((int)temp.getX()+250, (int)temp.getY()+250, 10, 10);
					System.out.println("CDAS is NOT strongly connected!!!");
					System.out.println("disconnectivity at node: "+temp.getID());
					System.out.println(temp.getID()+" has class number "+temp.getClassNum());
					double r = (double)g.getMinTransmissionRange()/g.getMaxTransmissionRange();
					int k= 1+ (int)Math.ceil(4.0/(Math.sqrt(3)*r));
					System.out.println(temp.getID()+"("+temp.getX()+", "+temp.getY()+") has class number :"+new ClassNumber().calculateClassNum(temp.getX(),temp.getY(), g.getMaxTransmissionRange(), k, r));
					System.out.println("Incomming neighbors: ");
					for(Vertex v:temp.getIncommingNeighbors())
						System.out.println(v.getID()+" "+ v.getClassNum()+" Status: "+v.getStatus());
					System.out.println("Outgoing neighbors: ");
					for(Vertex v:temp.getOutgoingNeighbors())
						System.out.println(v.getID()+" "+ v.getClassNum()+" Status: "+v.getStatus());
				}else System.out.println("CDAS IS STRONGLY CONNECTED");
				System.out.println("SIZE: "+SCDAS.size());
			}//end of if strongly connected
			else {System.out.println("The graph is not strongly disconnected");
			System.out.println("Node "+g.isStronglyConnected().getID()+" is not visited");
			}
			}catch(IOException e){e.printStackTrace();}
			
		}

	}
	 
		public Dimension getPreferredSize() {
				return new Dimension(WIDTH, HEIGHT);
		}

		// These set the size of the drawing area.
		// Change the sizes to suit what you need.
		private int WIDTH = 500;
		private int HEIGHT = 500;
	

	/**
	 * Creates a new window frame.
	 */
	public DrawGraph(String name) {
		super(name);
	}

	/**
	 * Terminates the program when the user wants to quit.
	 */
	private static void quit() {
		System.exit(0);
	}
	 public static void main(String[] args)
	  {

	      // Create the window frame with the label
	     // "My DrawLine". Change the text to change
	     // the label.
	     DrawGraph frame = new DrawGraph("UDG") ;

	     // Create the contents of the frame. The top (or Center)
	     // part is the drawing area. The bottom (or South) strip
	     // holds a quit button.
	       DrawArea drawing = new DrawArea() ;
	       frame.setLayout(new BorderLayout()) ;
	       frame.add("Center",drawing) ;
	    
	     // The event listeners are set up here to enable the
	     // program to respond to events.
	  

	     frame.addWindowListener(new WindowAdapter()
	     {
	       public void windowClosing(WindowEvent evt)
	       {
	         quit() ;
	       }
	     }) ;

	     frame.pack() ;
	     frame.setVisible(true) ;
	  }
}
