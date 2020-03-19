package ProjectManagement;
import java.util.ArrayList;

import PriorityQueue.*;
public class User_Heap extends MaxHeap<User>
{
	public ArrayList<UserReport_> search(int n)
    {
    	ArrayList<UserReport_> userlist = new ArrayList<>();
    	int index = 1;
    	for(int i = 0;i<n;i++)
    	{
    		int left = 2*i;
    		int right = left+1;
    		userlist.add(heap.get(index).value);
    		if(index == this.count())
    			return userlist;
    		if(heap.get(left).value.compareTo(heap.get(right).value) > 0)
    		{
    			index = left;
    		}
    		else index = right;
    	}
    	
    	return userlist;
    }
}