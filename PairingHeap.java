import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

class Node
{
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
class HeapNode
{
	Node element;
	HeapNode child;
	HeapNode leftSib;
	HeapNode rightSib;
	HeapNode(Node e,HeapNode c,HeapNode l,HeapNode r)
	{
		element=e;
		child=c;
		leftSib=l;
		rightSib=r;
	}
}
public class PairingHeap {
	
	HeapNode root=null;
	Map<String,String> codetable=new HashMap<>();
	
	public Map<String,String> buildTree(Map<String,Integer> freq_table)
	{
		Set<String> set=freq_table.keySet();
		for(String s:set)
		{
			Node temp = new Node(s,freq_table.get(s),null,null);
			insert(temp);
			
		}
	
		//print(root);
		while(root.child!=null)
			{
			Node l=removeMin();
			Node r=removeMin();
			Node internal=new Node("$",l.freq+r.freq,l,r);
			insert(internal);
			}
		//print(root);
		StringBuilder sb=new StringBuilder("");
		gen_code_table(root.element,sb);
		
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
			//System.out.println(head.data+" "+sb.toString());
			}
	}
	
	public Node removeMin()
	{
		Node temp=root.element;
		root=combine(root.child);
		return temp;
	}
	
	public HeapNode combine(HeapNode temp)
	{
		
		List<HeapNode> q=new LinkedList<>();
		if(temp==null)
			return null;
		else
			{
			while(temp!=null)
				{
				q.add(temp);
				temp=temp.rightSib;
				}
			while(!q.isEmpty())
				{
				HeapNode first=q.remove(0);			
				if(q.isEmpty())
					return first;
				HeapNode second=q.remove(0);
				HeapNode joined=meld(first,second);
				q.add(joined);
				}
			}
		return null;
	}
	
	public void print(HeapNode root)
	{
		List<HeapNode> q=new LinkedList<>();
	q.add(root);
	while(!q.isEmpty())
		{
		HeapNode temp=q.remove(0);
		System.out.println(temp.element.data+" "+temp.element.freq+" ");
		temp=temp.child;
		while(temp!=null)
			{
			q.add(temp);
			temp=temp.rightSib;
			}
		}
	}
	
	public void insert(Node temp)
	{
		HeapNode current=new HeapNode(temp,null,null,null);
		if(root==null)
			root=current;
		else
		{
			root=meld(root,current);
		}
	}
	
	public HeapNode meld(HeapNode first,HeapNode second)
	{
		if(second==null)
            return first;
 
		if(second.element.freq<first.element.freq)
			{
			//second.leftSib=first.leftSib;
			second.rightSib=null;
			second.leftSib=null;
			first.leftSib=second;
			first.rightSib=second.child;
			if(first.rightSib!=null)
				first.rightSib.leftSib=first;
			second.child=first;
			return second;
			}
		else
			{
			second.leftSib=first;
			/*first.rightSib=second.rightSib;
			if(first.rightSib!=null)
				first.rightSib.leftSib=first;*/
			first.rightSib=null;	
			first.leftSib=null;
			second.rightSib=first.child;
			if(second.rightSib!=null)
				second.rightSib.leftSib=second;
			first.child=second;
			return first;
			}
        
	}
}








