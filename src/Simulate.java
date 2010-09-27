import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Simulate {

	public static void main (String args[])
	 {
		TBLS_Unidirectional tbls_u = new TBLS_Unidirectional();
		int max_range=50;
		int locality=10;
		int errors=0;
		try{
			for (int min_range=10; min_range >=10; min_range-=10)
			{
			 //number of nodes
		    	for (int i=5; i<6;i++)
		    	{
		    		MyFile myFile=new MyFile(".\\Graphs("+min_range+"-50)\\graphFile("+min_range+"-50)"+ i +".txt");
		    		FileReader input = new FileReader(myFile.getFileName());
				    BufferedReader bufRead = new BufferedReader(input);
				   
				    MyFile result = new MyFile(".\\Graphs("+min_range+"-50)\\Statistics\\result("+min_range+"-50)"+(i+1)*50+".txt");
				    FileWriter fstream = new FileWriter(result.getFileName());
				    BufferedWriter out = new BufferedWriter(fstream);
				    out.write("DAS size with prunung locality "+locality+"\n");
				    
				    for (int j=0; j<100 ; j++)
				    {
				           Graph g = myFile.generateGraphFromFile(bufRead,max_range,min_range);
				           System.out.println("min_range "+ min_range+ "i: "+i +"Graph "+j);
				           Result res= tbls_u.heuristic(g, locality);
				           //check if the smallest DAS is connected and correct
				           
				          if (!tbls_u.isDASCorrect(g, res.getPrunedCDSs().lastElement())){
				        	  out.write(res.getCDS().size()+"\t  Error");
				        	  //write the graph to a file 
				        	  MyFile error = new MyFile(".\\Graphs("+min_range+"-50)\\Statistics\\error.txt"+errors);
							    FileWriter efstream = new FileWriter(error.getFileName());
							    BufferedWriter eout = new BufferedWriter(efstream);
							    error.addGraphToFile(g, eout);
							    eout.close();
							    errors++;
				          }else{
				             
				           
				           
				           
				        	  out.write(res.getCDS().size()+"\t");
				        	  for (int index=0; index<locality; index++)
				        		  out.write(res.getPrunedCDSs().get(index).size()+"\t");
				           }
				           out.write("\n");
				    }
				    out.close();
		    	}
		    	
			}
		}catch (IOException e){
			// If another exception is generated, print a stack trace
			e.printStackTrace();
		}
	 }
			
}
