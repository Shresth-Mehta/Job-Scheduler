package RedBlack;
import java.util.List;   


public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E>  {

	public RedBlackNode<T,E> root;

	public RBTree()
	{
		root = new RedBlackNode();
		root.colour = "black";
		root.parent = null;
	}

    @Override
    public void insert(T key, E value) {
    	
    	RedBlackNode<T,E> temp;
    	temp = binary_insert(key,value,root);
    	//System.out.println(temp.getKey().toString());
    	//System.out.println(root.right.getKey().toString());
    	if(temp != root && !ischild(temp,root))
    	{
    		restructure_2(temp);
    	}
    }
    private boolean ischild(RedBlackNode<T,E> child,RedBlackNode<T,E> node)
    {
    	if(root.left == child || root.right == child)
    		return true;
    	else return false;
    			
    }
    
//    private boolean same (RedBlackNode<T,E> a, RedBlackNode<T,E> b)
//    {
//    	if(a.getKey().compareTo(b.getKey()) == 0)
//    		return true;
//    	else
//    		return false;
//    }
    
    private RedBlackNode<T,E> sibling(RedBlackNode<T,E> node)
    {
    	
//    	if(same(node.parent.left, node))
//    		return node.parent.right;
//    	if(same(node.parent.right,node))
//    		return node.parent.left;
//    	else return null;
    	
    	if(node.parent.left == node)
    		return node.parent.right;
    	if(node.parent.right == node)
    		return node.parent.left;
    	else return null;
    }
    private void flip_colour(RedBlackNode<T,E> node)
    {
    	if(node.colour.compareTo("black") == 0)
    		node.colour = "red";
    	else
    		node.colour = "black";
    }
    
    private void rotate(RedBlackNode<T,E> node, String way)
    {
    	if(way.compareTo("left") == 0)
    	{
    		RedBlackNode<T,E> temp = node.parent;
    		RedBlackNode<T,E> K = temp.parent;
    		if(K != null)
    		{
    			if(temp.parent.right == temp)
    				K.right = node;
    			else if(temp.parent.left == temp)
    				K.left = node;
    		}
    		else 
    		{
    			root = node;
    		}
    		RedBlackNode<T,E> temp2 = node.left;
    		if(temp2 != null)
    			temp2.parent = temp;
    		node.left = temp;
    		node.parent = K;
    		temp.parent = node;
    		temp.right = temp2;
    		
    	}
    	
    	else if(way.compareTo("right") == 0)
    	{	
    		RedBlackNode<T,E> temp = node.parent;
    		RedBlackNode<T,E> K = temp.parent;
    		if(K != null)
    		{
    			if(temp.parent.right == temp)
    				K.right = node;
    			else if(temp.parent.left == temp)
    				K.left = node;
    		}
    		else 
    		{
    			root = node;
    		}
    		RedBlackNode<T,E> temp2 = node.right;
    		if(temp2 != null)
    			temp2.parent = temp;
    		node.right = temp;
    		node.parent = K;
    		temp.parent = node;
    		temp.left = temp2;
    	}
    }
    
    private void restructure(RedBlackNode<T,E> node)
    {
    	RedBlackNode<T,E> uncle = sibling(node.parent);
    	if (uncle != null)
    	{
			if (node.parent.colour.compareTo("red") == 0) {
				if (uncle.colour.compareTo("red") == 0) {
					flip_colour(node.parent);
					flip_colour(uncle);
					flip_colour(node.parent.parent);
					if(node.parent.parent == root)
						root.colour = "black";
					else 
						restructure(node.parent.parent);
				} else if (uncle.colour.compareTo("black") == 0) {
					RedBlackNode<T, E> grandparent = node.parent.parent;
					if (grandparent.left.left == node) 
					{	
						rotate(node.parent,"right");
					} 
					else if (grandparent.right.right == node)
					{
						rotate(node.parent,"left");
					} 
					else if (grandparent.left.right == node) 
					{
						rotate(node,"left");
						rotate(node,"right");
					} 
					else if (grandparent.right.left == node) 
					{
						rotate(node,"right");
						rotate(node,"left");
					}
				}

			} 
		}
    	else
    	{
    		if(node.parent.colour.compareTo("red") == 0)
    		{
    			flip_colour(node.parent);
    			flip_colour(node.parent.parent);
    			RedBlackNode<T, E> grandparent = node.parent.parent;
				if (grandparent.left != null && grandparent.left.left != null && grandparent.left.left == node) 
				{
					rotate(node.parent,"right");
				} 
				else if (grandparent.right != null && grandparent.right.right != null && grandparent.right.right == node) 
				{
					rotate(node.parent,"left");
				}
				else if (grandparent.left != null && grandparent.left.right != null && grandparent.left.right == node) 
				{
					rotate(node,"left");
					rotate(node,"right");
				}
				else if (grandparent.right != null && grandparent.right.left != null && grandparent.right.left == node) 
				{
					rotate(node,"right");
					rotate(node,"left");
				}

    		}
    	}
    }
    private void c_swap(RedBlackNode<T,E> a,RedBlackNode<T,E> b)
    {
    	String temp = a.colour;
    	a.colour = b.colour;
    	b.colour = temp;
    }
    
    
    
    
    
    private void restructure_2(RedBlackNode<T,E> node)
    {
    	if(node == root || (node.parent != null && node.parent == root))
    	{
    		root.colour = "black";
    		return ;
    	}
    	RedBlackNode<T,E> uncle = sibling(node.parent);
    		
		if (node.parent.colour.compareTo("red") == 0)
		{
			if (uncle == null || uncle.colour.compareTo("black") == 0) 
			{
				RedBlackNode<T, E> grandparent = node.parent.parent;
				if (grandparent.left != null && grandparent.left.left != null && grandparent.left.left == node) 
					{	
						c_swap(node.parent,grandparent);
						rotate(node.parent,"right");
					} 
					else if (grandparent.right != null && grandparent.right.right != null && grandparent.right.right == node)
					{
						c_swap(node.parent,grandparent);
						rotate(node.parent,"left");
					} 
					else if (grandparent.left != null && grandparent.left.right != null && grandparent.left.right == node) 
					{
						rotate(node,"left");
						c_swap(node,grandparent);
						rotate(node,"right");
					} 
					else if (grandparent.right != null && grandparent.right.left != null && grandparent.right.left == node) 
					{
						rotate(node,"right");
						c_swap(node,grandparent);
						rotate(node,"left");
					}
				}
			else if (uncle.colour.compareTo("red") == 0) 
			{
				node.parent.colour = "black";
				uncle.colour = "black";
				node.parent.parent.colour = "red";
				node = node.parent.parent;
				restructure_2(node);
			} 
			
		} 
		
    }
    
    private RedBlackNode<T,E> binary_insert(T key, E value, RedBlackNode<T,E> node)
    {
    	//System.out.println(key.toString());
    	if(node.getKey() == null)
    	{
    		//System.out.println("null ");
    		node.key = key;
    		node.values.add(value);
    		return node;
    	}
    	if(node.key.compareTo(key) < 0)
    	{
    		//System.out.println("right");
    		
    		if(node.right == null) 
    		{
    			
    			node.right = new RedBlackNode<T,E>();
    			node.right.parent = node;
    		}
    		return binary_insert(key,value,node.right);
    	}
    	else if(node.key.compareTo(key) > 0)
    	{
    		//System.out.println("left");
    		if(node.left == null)
    		{
    			node.left = new RedBlackNode();
    			node.left.parent = node;
    		}
    		return binary_insert(key,value,node.left);
    	}
    	else
    	{
    		//System.out.println("this ");
    		node.values.add(value);
    		return node;
    	}
    }

    @Override
    public RedBlackNode<T, E> search(T key) 
    {
        return Bst_search(key,root);
    }
    private RedBlackNode<T,E> Bst_search(T key,RedBlackNode<T,E> node)
    {
    	if(node.getKey() == null)
    		return new RedBlackNode<T,E>();
    	if(node.getKey().compareTo(key) == 0)
    		return node;
    	if(node.getKey().compareTo(key) > 0)
    		return Bst_search(key,node.left);
    	if(node.getKey().compareTo(key) < 0)
    		return Bst_search(key,node.right);
    	return new RedBlackNode<T,E>();
    }
    
    public void print(RedBlackNode<T,E> node)
    {
    	if(node == null)
    		return ;
    	//System.out.println("yes");
    	List<E> list = node.getValues();
    	System.out.print(node.getKey()+ " "+node.colour+": ");
    	for(int i = 0;i<list.size();i++)
    	{
    		System.out.print(list.get(i)+" ");
    	}
    	System.out.println(" ");
    	print(node.left);
    	print(node.right);
    }
}