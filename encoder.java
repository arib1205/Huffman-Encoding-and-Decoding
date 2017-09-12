import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class encoder {

	
	public static void main(String[] args){
		
		final String inputfile=args[0];
		
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
		
		
		Map<String,String> codeTable = build_using_4heap(freq_table);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("code_table.txt"))) {
			
			Set<String> set=codeTable.keySet();
			for(String s:set)
			{
				writer.write(s+" "+codeTable.get(s)+"\n");	
			}
	   
	   
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
		
		StringBuffer encode=new StringBuffer();
	
		try (BufferedReader br = new BufferedReader(new FileReader(inputfile))) {

			String curLine;

			while ((curLine = br.readLine()) != null) {
				if(codeTable.containsKey(curLine))
				encode.append(codeTable.get(curLine));
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BitSet bitset=new BitSet();
		for(int i=0;i<encode.length();i++)
		{
			if(encode.charAt(i)=='1')
				bitset.set(i);
		}
		
		
		byte[] byteArray=bitset.toByteArray();
				
		File file=new File("encoded.bin");
		
		try{
			FileOutputStream fileOs=new FileOutputStream(file);
			DataOutputStream os=new DataOutputStream(fileOs);
			os.write(byteArray);
			os.close();
		}
		catch(FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static Map<String,String> build_using_4heap(Map<String,Integer> freq_table)
	{
		FourHeap fh=new FourHeap();
		Map<String,String> codeTable=fh.buildTree(freq_table);

		return codeTable;
	}
}
