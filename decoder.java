import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class HuffNode{
	String data;
	HuffNode left;
	HuffNode right;
	HuffNode(String data)
	{
		this.data=data;
		left=null;
		right=null;
	}
}
public class decoder {

	static HuffNode root=null;
	
	static String s;
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		File encode=new File(args[0]);
		File code=new File(args[1]);
	
		Map<String,String> codeTable= new HashMap<>();
		StringBuffer sb = new StringBuffer();
		
		
		try(BufferedReader br=new BufferedReader(new FileReader(code))){
			
			String curLine;
			
			while((curLine = br.readLine()) != null)
			{
				String[] sp=curLine.split(" ");
				codeTable.put(sp[0], sp[1]);
			}
		
			//build huffman tree
			Set<String> set=codeTable.keySet();
			for(String s:set)
				{
				root=buildTree(s,codeTable.get(s),root,null,-1);
				}
	
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//print(root);
		
		
		//Read encoded.bin
		try{
			FileInputStream fileIs = new FileInputStream(encode);
			DataInputStream is = new DataInputStream(fileIs);
			
			byte[] enc = new byte[(int) encode.length()];
			is.readFully(enc);
			
			BitSet bitset = BitSet.valueOf(enc);
			
			
			for(int i=0;i<bitset.length();i++)
			{
				if(bitset.get(i)==true)
					sb.append("1");
				else
					sb.append("0");
			}
			
			//Add zeroes upto 8 bits...since bitset will help in setting up the last 1.
			while(sb.length()!=(8*enc.length))
			{
				sb.append("0");
			}
			
			is.close();
		}
		catch(FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		s=new String(sb);
		
		//generating decode.txt by traversing tree
		try(BufferedWriter writer = new BufferedWriter(new FileWriter("decoded.txt"))){
		
			for(int i=0;i<s.length();)
			{
				String val = getValue(root,i);
				writer.write(val+"\n");
				i=i+codeTable.get(val).length();
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//function to traverse tree to get value
	public static String getValue(HuffNode root, int i)
	{
	
		if(root.data!="$")
			return root.data;
		
		String ret="";
		
		if(s.charAt(i)=='0')
			ret = getValue(root.left,i+1);
		else if(s.charAt(i)=='1')
			ret = getValue(root.right,i+1);
		
		return ret;
		
		
	}
	
	
	//Print Huffman Tree
	public static void print(HuffNode head)
	{
		if(head==null)
			return;
		print(head.left);
		System.out.println(head.data);
		print(head.right);
	}
	
	
	//Building Huffman Tree from code table
	public static HuffNode buildTree(String value, String code, HuffNode head, HuffNode prev,int left)
	{
		if(code.length()==0)
			{
			head=new HuffNode(value);
			if(left==0)
				prev.left=head;
			else if(left==1)
				prev.right=head;
			return head;
			}
		if(head==null)
			{
			head=new HuffNode("$");
			if(left==0)
				prev.left=head;
			else if(left==1)
				prev.right=head;
			}
		
		if(code.charAt(0)=='0')
			buildTree(value,code.substring(1, code.length()),head.left,head,0);
		else if(code.charAt(0)=='1')
			buildTree(value,code.substring(1, code.length()),head.right,head,1);
		
		return head;
	}

}
