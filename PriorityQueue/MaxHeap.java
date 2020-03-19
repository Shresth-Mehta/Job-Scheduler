package PriorityQueue;
import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> implements PriorityQueueInterface<T> {

	public ArrayList<HeapNode<T>> heap;
	private int p_count;
	public MaxHeap()
	{
		heap = new ArrayList<HeapNode<T>>();
		HeapNode<T> rootnode =  new HeapNode<>(0,null);
		heap.add(rootnode);
		p_count = 0;
	}
    @Override
    public void insert(T element) 
    {	
    	HeapNode<T> node =  new HeapNode<T>(p_count,element);
    	//System.out.println(p_count);
    	p_count += 1;
    	heap.add(node);
    	//System.out.println(count());
    	bubble_up(this.count());
    	//print();
    	//System.out.println();

    }
    public int count()
    {
    	return heap.size()-1;
    }
    
    private void swap(int a,int b)
    {
    	HeapNode<T> temp = heap.get(a);
    	heap.set(a,heap.get(b));
    	heap.set(b,temp);
    }
    
    private void bubble_up(int index)
    {
    	int parent_index = index/2;
    	int current_index = index;
    	//System.out.print(current_index +" "+parent_index);
    	while(current_index > 0 && parent_index > 0 && heap.get(current_index).compareTo(heap.get(parent_index)) > 0)
    	{
    		swap(current_index,parent_index);
    		current_index = parent_index;
    		parent_index = parent_index/2;
    	}
    		
    }
    
    public void print()
    {
    	System.out.println();
    	for(int i =1;i<=count();i++)
    	{
    		System.out.println(heap.get(i).value.toString());
    	}
    	System.out.println();
    }

    @Override
    public T extractMax() 
    {
    	if(heap.size() == 1)
    		return null;
    	HeapNode<T> max = heap.get(1);
    	heap.set(1,heap.get(count()));
    	heap.remove(count());
    	push_down(1);
    	//print();
    	return max.value;
    }
    
    private void push_down(int index)
    {
    	int head = index;
    	int left = 2*index;
    	int right = 2*index+1;
    	
    	if(right <= this.count() && heap.get(head).compareTo(heap.get(right)) < 0)
    	{
    		head = right;
    	}
    	
    	//System.out.print(heap.get(head).priority +""+heap.get(left).priority);
    	if(left <= this.count() && heap.get(head).compareTo(heap.get(left)) < 0)
    	{
    			head = left;
    	}
    	//System.out.print(heap.get(head).priority +""+heap.get(left).priority);
    	
    	if(head != index)
    	{
    		//System.out.println(head+" "+left+" "+right);
    		swap(index,head);
    		push_down(head);
    	}
    }
    
    public void heapify(int index)
    {
    	push_down(index);
    }
    

}