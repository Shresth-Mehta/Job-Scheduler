package trie_2;
import java.util.ArrayList;
public class Trie<T>
{
	Node<T> root;
	public Trie()
	{
		root = new Node<>();
		//System.out.println(root.content.size());
	}
	
	public boolean insert(String key, T value)
	{
		char[] arr = key.toCharArray();
		int len = key.length();
		
		Node<T> temp = root;
		for(int i = 0;i<len;i++)
		{
			int code = hash(arr[i]);
			Node<T> temp2 = temp.content.get(code);
			if(temp2 == null)
			{
				Node<T> newNode = new Node<T>();
				temp.content.set(code, newNode);
				temp = newNode;
			}
			else 
				temp = temp2;
		}
		if(temp.value == null)
		{
			//System.out.println("called");
			temp.value = value;
			return true;
		}
		return false;
	}
	
	public Node<T> search(String key)
	{
		char[] arr = key.toCharArray();
		int len = key.length();
		Node<T> temp = root;
		for(int i = 0;i<len;i++)
		{	int code = hash(arr[i]);
			Node<T> temp2 = temp.content.get(code);
			if(temp2 == null)
				return null;
			temp = temp2;
		}
		if(temp.getValue() == null)
			return null;
		else
			return temp;
	}
	
	private int hash(char s)
	{
		return (int)s-32;
	}
	
	public boolean delete(String key)
	{
		char[] arr = key.toCharArray();
		int len = key.length();
		Node<T> temp = root;
		Node<T> delete_from = null;
		char delete_key ='$';
		for(int i = 0;i<len;i++)
		{
			if(temp == null)
				return false;
			if(temp.value != null)
			{
				delete_from = temp;
				delete_key = arr[i];
			}
			temp = temp.content.get(hash(arr[i]));
		}
		
		if(temp.value != null && delete_from != null && delete_key != '$')
		{
			delete_from.content.set(hash(delete_key),null);
			temp.value = null;
			return true;
		}
		else return false;
	}
	
	public void print()
	{
		int n = height(root);
		ArrayList<String> a = new ArrayList<>(n);
		for(int i = 0;i<n;i++)
			a.add("");
		print_(root,a,0);
		//System.out.println(a.size());
		for(String x:a)
			System.out.println(x);
	}
	
	private void print_(Node<T> node,ArrayList<String> arr,int level)
	{
		if(node == null)
			return ;
		int len = node.content.size();
		for(int i = 0;i<len;i++)
		{
			if(node.content.get(i) != null)
			{
				arr.set(level, arr.get(level)+" "+dehash(i));
				print_(node.content.get(i),arr,level+1);
			}
		}
		return;
	}
	
	private char dehash(int i)
	{
		i +=32;
		return (char)i;
	}
	
	private int height(Node<T> node)
	{
		if(node == null)
			return -1;
		int max = -1;
		int len = node.content.size();
		for(int i =0;i<len;i++)
		{
			max = Math.max(max, height(node.content.get(i)));
			//System.out.println(max);
		}
		return max+1;
	}
}