import java.io.*;
import java.util.*;

/**
 program to find word ladder with shortest path (i.e. minimum number edges)
 */
public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {

		long start = System.currentTimeMillis();

		String inputFileName = args[0]; // dictionary
		String startWord = args[1]; // first word
		
		String endWord = args[2]; // second word
		FileReader reader = new FileReader(inputFileName);
		Scanner in = new Scanner(reader);
		
		// read in the data here
		ArrayList<String> words = new ArrayList<String>(); 	//startWordintermittent to avoid overcomplicating file usage
		
		while (in.hasNext()){
			words.add(in.nextLine());
		}
		
        // create graph here
		Graph G = new Graph(words.size());
		int[] wordPositions = populateGraph(G, words, startWord, endWord);
		int startingWordPosition = wordPositions[0];
		int endingWordPosition = wordPositions[1];
		
		in.close();
        reader.close();

		// do the work here
		Vertex result = G.backTrackAlgorithm(startWord, endWord, startingWordPosition, endingWordPosition);
		
		printResult(G,result,startWord,endWord);
		
		
		// end timer and print total time
		long end = System.currentTimeMillis();
		System.out.println("\nElapsed time: " + (end - start) + " milliseconds");
		
	}

	//populate the graph with words from the List and return the indexes of the start and end word
	private static int[] populateGraph(Graph G, ArrayList<String> words, String startWord, String endWord) {
		String wordOne, wordTwo;
		int startingWordPosition = 0, endingWordPosition = 0;
		int[] positions = new int[2];
		for (int i=0; i < words.size(); i++){
			//populate the graph with vertices which will hold the words
			wordOne = words.get(i);
			G.getVertex(i).setWord(wordOne); //place all words in the already created vertices
			
			//getting the indexes of the start & end word to use in bfs
			if (wordOne.equals(startWord))
				startingWordPosition = i;
			
			if (wordOne.equals(endWord))
				endingWordPosition = i;
			
			for (int j=0; j < words.size(); j++){
				wordTwo = words.get(j);
				if (OneLetterDifference(wordOne, wordTwo)){
					G.getVertex(i).addToAdjList(j);
					G.getVertex(j).addToAdjList(i);
				}
			}
		}
		positions[0] = startingWordPosition;
		positions[1] = endingWordPosition;
		return positions;
		}

	public static boolean OneLetterDifference(String firstWord, String secondWord){
		
    	int letterDifference = 0;
    	for (int i=0; i<firstWord.length(); i++){
    		if (firstWord.charAt(i) != secondWord.charAt(i))
    			letterDifference++;
    	}
    	if (letterDifference == 1)
    		return true;
    	return false;
    }
	
	public static void printResult(Graph G,Vertex result, String startWord, String endWord){
		
		if (result != null){
		
			List<String> resultList = new ArrayList<>();
			Vertex stepperVertex = result;
			while (!stepperVertex.getWord().equals(startWord)){
				resultList.add(stepperVertex.getWord());
				stepperVertex = G.getVertex(stepperVertex.getPredecessor());
			}
			resultList.add(startWord);
			Collections.reverse(resultList);
			
			System.out.println("Length of the shortest path: " + (resultList.size()-1));
			
			for(String s: resultList){	
				System.out.print(s);
			
				if (!s.equals(endWord))
					System.out.print(" - > ");
			}
		}
		else
			System.out.println("No ladder is possible.");
	}
	
	
}
