import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BinHeap {
	
	class Node{
		String data;
		int freq;
		Node left;
		Node right;
		
		Node(String d,int f, Node l, Node r)
		{
			data=d;
			freq=f;
			left=l;
			right=r;
		}
	}
	
	Node[] heap;
	int heapsize=0;
	private static final int d = 2;
	Map<String,String> codetable =new HashMap<>();
	
	public int parent(int i)
	{
		return (i-1)/d;
	}
	
	public int KthChild(int i,int k)
	{
		return (d*i)+k;
	}
	
	public Map<String,String> buildTree(Map<String,Integer> freq_table)
	{
		heap = new Node[freq_table.size()];
		int k=0;
		
		Set<String> set=freq_table.keySet();
		for(String s:set)
		{
			heap[k] = new Node(s,freq_table.get(s),null,null);
			heapsize++;
			k++;
		}
		
		for(int i=heapsize/2-1;i>=0;i--)
		{
			minHeapify(i);
		}
		
		while(heapsize!=1)
		{
			Node l=removeMin();
			Node r=removeMin();
			Node internal=new Node("$",l.freq+r.freq,l,r);
			insert(internal);			
		}
				
		StringBuilder sb=new StringBuilder("");
		gen_code_table(heap[0],sb);
			
		return codetable;
	
	}
	
	public void gen_code_table(Node head,StringBuilder sb)
	{
		if(head==null)
			return;
		if(head.data.equals("$"))
			{
			sb.append("0");
			gen_code_table(head.left,sb);
			sb.deleteCharAt(sb.length()-1);
			
			sb.append("1");
			gen_code_table(head.right,sb);
			sb.deleteCharAt(sb.length()-1);
			}
		else
			{
			codetable.put(head.data,sb.toString());
			}
	}
	
	
	public void insert(Node internal)
	{
		heapsize++;
				
		int i=heapsize-1;
		int p=parent(i);
		
		while(i>0 && heap[p].freq > internal.freq)
		{
			heap[i]=heap[p];
			i=p;
			p=parent(p);
		}
		heap[i]=internal;
	}
	
	public void minHeapify(int p)
	{
		int l=KthChild(p,1);
		int r=KthChild(p,2);
		int smallest=p;
		
		if(l<heapsize && heap[p].freq > heap[l].freq)
			smallest=l;
		if(r<heapsize && heap[smallest].freq > heap[r].freq)
			smallest=r;
		
		if(smallest!=p)
			{
			Node temp=heap[p];
			heap[p]=heap[smallest];
			heap[smallest]=temp;
			minHeapify(smallest);
			}
	}
	
	public Node removeMin()
	{
		Node temp = heap[0];
		heap[0]=heap[heapsize-1];
		heapsize--;
		minHeapify(0);
		return temp;
	}
	
}
