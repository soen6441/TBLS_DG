import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;


public class Statistics {
	
	public static void main (String args[])
	 {
		int max_range=50;
		try{
			for (int min_range=10; min_range <=50; min_range+=10)
			 //number of nodes
		    	for (int i=0; i<6;i++)
		    	{
		    		MyFile myFile=new MyFile(".\\Graphs("+min_range+"-50)\\graphFile("+min_range+"-50)"+ i +".txt");
		    		FileReader input = new FileReader(myFile.getFileName());
				    BufferedReader bufRead = new BufferedReader(input);
				   
				    MyFile result = new MyFile(".\\Graphs("+min_range+"-50)\\Statistics\\result("+min_range+"-50)"+(i+1)*50+".txt");
				    FileWriter fstream = new FileWriter(result.getFileName());
				    BufferedWriter out = new BufferedWriter(fstream);
				    out.write("UnidirectionalPercentage"+"\t"+"AvgTransRange"+"\t"+"AvgIncomingDegree"+"\t"+"AvgNumOfNeighbors"+"\n");
				   
				    for (int j=0; j<100 ; j++)
				    {
				           Graph g = myFile.generateGraphFromFile(bufRead,max_range,min_range);
				          
							   double unidirectional =0;
							   for (Edge e:g.getEdges())
								   if (!e.reverseEdge().isInListUnidirectional(g.getEdges()))
									   ++ unidirectional;
								double bidirectional = (g.getEdges().size()-unidirectional)/2;
								double unidirectionalPercentage = (unidirectional/(unidirectional+bidirectional))*100;
								
								double sumTransmissionRange =0;
								double sumOfIncoming = 0;
								double sumOfNeighbors = 0;
								for (Vertex v:g.getVertices())
								{
									sumTransmissionRange += v.getTransmissionRange();
									sumOfIncoming += v.getN_d().size();// would be equal to the sum of outgoing edges
									sumOfNeighbors += v.getN_a_U_N_d().size();
								}
								double avgTransmissionRange = sumTransmissionRange/g.getVertices().size();
								double avgIncomingDegree = sumOfIncoming/g.getVertices().size(); // would be equal to the average of outgoing edges
								double avgNumOfNeighbros = sumOfNeighbors/g.getVertices().size();
								
								
							    out.write((int)unidirectionalPercentage+"\t"+(int)avgTransmissionRange+"\t"+(int)avgIncomingDegree+"\t"+(int)avgNumOfNeighbros +"\n");
								
				    }
				    System.out.println("Finished collecting statisticss for series "+i);
				    out.close();
				  				    
				}
		    	}catch (IOException e){
		             // If another exception is generated, print a stack trace
		            e.printStackTrace();
		          }
	 }

}
