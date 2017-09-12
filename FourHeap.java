import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FourHeap {

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
	private static final int d = 4;
	Map<String,String> codetable =new HashMap<>();
	
	public int parent(int i)
	{
		return (i-3-1)/d+3;
	}
	
	public int KthChild(int i,int k)
	{
		return (d*(i-3))+3+k;
	}
	
	public Map<String,String> buildTree(Map<String,Integer> freq_table)
	{
		heap = new Node[freq_table.size()+3];
		int k=3;
		
		Set<String> set=freq_table.keySet();
		for(String s:set)
		{
			heap[k] = new Node(s,freq_table.get(s),null,null);
			heapsize++;
			k++;
		}
		
		for(int i=heapsize/4-1+3;i>=3;i--)
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
		gen_code_table(heap[3],sb);
				
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
		
		int i=heapsize-1+3;
		int p=parent(i);
		
		while(i>3 && heap[p].freq > internal.freq)
		{
			heap[i]=heap[p];
			i=p;
			p=parent(p);
		}
		heap[i]=internal;
	}
	
	public Node removeMin()
	{
		Node temp = heap[3];
		heap[3]=heap[heapsize-1+3];
		heapsize--;
		minHeapify(3);
		return temp;
	}
	
	public void minHeapify(int p)
	{
		int first=KthChild(p,1);
		int second=KthChild(p,2);
		int third=KthChild(p,3);
		int fourth=KthChild(p,4);
		
		int smallest=p;
		
		if(first<heapsize+3 && heap[p].freq > heap[first].freq)
			smallest=first;
		if(second<heapsize+3 && heap[smallest].freq > heap[second].freq)
			smallest=second;
		if(third<heapsize+3 && heap[smallest].freq > heap[third].freq)
			smallest=third;
		if(fourth<heapsize+3 && heap[smallest].freq > heap[fourth].freq)
			smallest=fourth;
		
		if(smallest!=p)
			{
			Node temp=heap[p];
			heap[p]=heap[smallest];
			heap[smallest]=temp;
			minHeapify(smallest);
			}
	}
	
	
}