import java.io.*;
import java.util.*;

import javax.sound.midi.Transmitter;
public class MyFile {
	
	String fName;
	Graph graph;
	final static int width = 200;
	final static int length = 200;
	double transmissionRange;
	int numOfNodes;
	double X;
	double Y;
	Vector<Vertex> vertices;


public MyFile (String fileName){
	this.fName = fileName;
	
	
}
public String getFileName()
{
	return this.fName;
}
public void writeNGraphsToFile(int n, int numOfNodes, int minRange, int maxRange,  double width, double length)
{
	try{
	    // Create file 
	    FileWriter fstream = new FileWriter(fName);
	    BufferedWriter out = new BufferedWriter(fstream);
	    for (int i=0; i<n;i++)
	    {
	    	Graph g= new Graph(numOfNodes, minRange, maxRange, width, length);
	    	if (g.isStronglyConnected().getID()==-1)
	    		{
	    		   addGraphToFile(g, out);
	    		   System.out.println("Graph "+i+ " generated successfully");
	    		}
	    	else
	    		i--;
	    }
	    out.close();
	}catch (Exception e){//Catch exception if any
	      System.err.println("Error: " + e.getMessage());
    }
}
public void addGraphToFile(Graph g,BufferedWriter out )
{
	this.graph = g;  
	try{
		    out.write(graph.getVertices().size()+"\n");
		    for (Vertex v:graph.getVertices())
		    	out.write(v.getX()+", "+v.getY()+"; "+v.getTransmissionRange()+ "\n");
		    //Close the output stream
		    
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		    }
}

public void writeGraphToFile(Graph g)
{
	this.graph = g;  
	try{
		    // Create file 
		    FileWriter fstream = new FileWriter(fName);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(graph.getVertices().size()+"\n");
		    for (Vertex v:graph.getVertices())
		    	out.write(v.getX()+", "+v.getY()+"; "+v.getTransmissionRange()+ "\n");
		    //Close the output stream
		    out.close();
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		    }
}

public Graph generateGraphFromFile(BufferedReader bufRead, int max_range, int min_range)
{
	try {
		  
		  int id=0;
		  
		  vertices = new Vector<Vertex>();
		  String line;    // String that holds current file line
		  /*// Read first line (transmission range)
		  line = bufRead.readLine();
		  transmissionRange = Integer.valueOf(line);*/
		  //Read number of nodes
		  line = bufRead.readLine();
		  numOfNodes = Integer.valueOf(line);
		  // Read through file one line at time. 
		  for (int i=0; i<numOfNodes ;i++){
			   line = bufRead.readLine();
			   X = Double.valueOf(line.substring(0, line.indexOf(",")));
		       Y = Double.valueOf(line.substring(line.indexOf(",")+1, line.indexOf(";")));
		       transmissionRange = Double.valueOf(line.substring(line.indexOf(";")+1, line.length()));
		       Vertex vertex = new Vertex(id, transmissionRange, X, Y);
		       if (X>100)
		    	   System.out.println("Erorrrrrrrrrrrrrrrrrrrrrrrrrrrrrr! X>100");
		       if (Y>100)
		    	   System.out.println("Erorrrrrrrrrrrrrrrrrrrrrrrrrrrrrr! Y>100");
		       vertices.add(vertex);
		       ++ id;
		       
		  }
		 	     
		         
		 
		          }catch (IOException e){
		             // If another exception is generated, print a stack trace
		            e.printStackTrace();
		          }
		 return (new Graph(vertices, max_range, min_range));         

}

public Graph generateGraphFromFile(int max_range, int min_range)
{
	try {
		  
		  int id=0;
		  
		  vertices = new Vector<Vertex>();
		  
		  BufferedReader bufRead =  new BufferedReader(new FileReader(this.getFileName()));
		  System.out.println("file  was found");
		  
		  String line;    // String that holds current file line
		  // Read first line (transmission range)
		  line = bufRead.readLine();
		  transmissionRange = Integer.valueOf(line);
		  //Read number of nodes
		  line = bufRead.readLine();
		  numOfNodes = Integer.valueOf(line);
		  // Read through file one line at time. 
		  while ((line = bufRead.readLine())!= null){
			   X = Double.valueOf(line.substring(0, line.indexOf(",")));
		       Y = Double.valueOf(line.substring(line.indexOf(",")+1, line.indexOf(";")));
		       transmissionRange = Integer.valueOf(line.substring(line.indexOf(";")+1, line.length()));
		       Vertex vertex = new Vertex(id, transmissionRange, X, Y);
		       vertices.add(vertex);
		       ++ id;
		       
		  }
		  bufRead.close();
		     
		         
		 
		          }catch (IOException e){
		             // If another exception is generated, print a stack trace
		            e.printStackTrace();
		          }
		 return (new Graph(vertices,max_range,min_range));         

}

}
