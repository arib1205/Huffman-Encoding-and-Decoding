import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class HuffmanCoding {

	//private static final String inputfile="C:\\Users\\aribp\\Desktop\\Spring 17\\ADS\\Project\\sample1\\sample_input_small.txt";
	
	private static final String inputfile="C:\\Users\\aribp\\Desktop\\Spring 17\\ADS\\Project\\sample2\\sample_input_large.txt";
	
	
	public static void main(String[] args) {
		
		Map<String,Integer> freq_table = new HashMap<>();	
		
		try (BufferedReader br = new BufferedReader(new FileReader(inputfile))) {

			String curLine;

			while ((curLine = br.readLine()) != null) {
				
				if(curLine.equals(""))
					continue;
				else if(freq_table.containsKey(curLine))
					{
					int temp = freq_table.get(curLine);
					freq_table.put(curLine,temp+1);
					}
				else
					{
					freq_table.put(curLine,1);
					}
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		long startTime = System.nanoTime();
		for(int i=0;i<10;i++)
			build_using_binheap(freq_table);
		long endTime = System.nanoTime();

		System.out.println((endTime - startTime)+" nanoseconds");
		
		startTime = System.nanoTime();
		for(int i=0;i<10;i++)
			build_using_4heap(freq_table);
		endTime = System.nanoTime();
		
		System.out.println((endTime - startTime)+" nanoseconds");
		
		startTime = System.nanoTime();
		for(int i=0;i<10;i++)
			build_using_pairingheap(freq_table);
		endTime = System.nanoTime();
		
		System.out.println((endTime - startTime)+" nanoseconds");
	}
	
	public static void build_using_binheap(Map<String,Integer> freq_table)
	{
		BinHeap bh=new BinHeap();
		Map<String,String> codeTable=bh.buildTree(freq_table);
		
	}	
	
	public static void build_using_4heap(Map<String,Integer> freq_table)
	{
		FourHeap fh=new FourHeap();
		Map<String,String> codeTable=fh.buildTree(freq_table);
		
	}
	
	public static void build_using_pairingheap(Map<String,Integer> freq_table)
	{
		PairingHeap ph=new PairingHeap();
		Map<String,String> codeTable=ph.buildTree(freq_table);
		
	}

}
