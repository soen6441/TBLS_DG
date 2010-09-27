public class GenerateFiles {

	public static void main(String args[]) {
		int numOfNodes = 50;
		for (int i = 0; i < 6; i++) {
			System.out.println("generating files for n: "+numOfNodes);
			MyFile myFile = new MyFile("graphFile(50-50)" + i + ".txt");
			myFile.writeNGraphsToFile(100, numOfNodes, 30, 30, 200, 200);
			numOfNodes += 50;
		}
		System.out.println("finished generating graphs");
	}
}
