package RedBlack;

import Util.RBNodeInterface;
import java.util.ArrayList;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {

	public String colour;
	ArrayList<E> values;
	public RedBlackNode<T,E> right = null;
	public RedBlackNode<T,E> left = null;
	public T key = null;
	public RedBlackNode<T,E> parent = null;
	
	public RedBlackNode()
	{
		this.values = new ArrayList<E>();
		this.colour = "red";
	}
	public RedBlackNode(T key, E value)
	{
		this.values = new ArrayList<E>();
		this.values.add(value);
		this.colour = "red";
		this.key = key;
	}
    @Override
    public E getValue() {
        return getValues().get(0);
    }

    @Override
    public ArrayList<E> getValues() {
    	
        return values;
    }
    
    public String getColour()
    {
    	return colour;
    }
    
    public void swap()
    {
    	if(colour.compareTo("black") == 0)
    	{
    		colour = "red";
    	}
    	else if(colour.compareTo("red") == 0)
    	{
    		colour = "black";
    	}
    }
    public T getKey()
    {
    	return key;
    }
    
    public RedBlackNode<T,E> parent()
    {
    	return parent;
    }
}
