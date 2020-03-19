package PriorityQueue;

public class HeapNode<T extends Comparable> implements Comparable<HeapNode<T>>
{
	public T value;
	public int priority;
	
	public HeapNode(int priority, T value)
	{
		this.value = value;
		this.priority = priority;
	}
	
	@Override
	public int compareTo(HeapNode<T> node)
	{
		if(this.value.compareTo(node.value) > 0) 
			return 1;
		if(this.value.compareTo(node.value) < 0)
			return -1;
		else 
		{
			if(this.priority > node.priority)
				return -1;
			else 
				return 1;
		}
	}
	
	
	
}