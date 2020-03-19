package ProjectManagement;

import java.util.Comparator;

public class CompareJobReport implements Comparator<JobReport_>
{
	public int compare(JobReport_ x,JobReport_ y)
	{
		if(x.completion_time() == 0)
			return -1;
		if(y.completion_time() == 0)
			return 1;
		if(x.completion_time()>y.completion_time())
			return 1;
		else 
			return -1;
		
	}
}