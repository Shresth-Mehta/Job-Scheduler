package trie_2;
import java.util.ArrayList;

public class Node<T>
{
	public ArrayList<Node<T>> content;
	public T value;
	
	public Node()
	{
		value = null;
		content = new ArrayList<>(95);
		for(int i = 0;i<95;i++)
		{
			content.add(null);
		}
	}
	public T getValue()
	{
		return value;
	}
	
}