package ProjectManagement;
import java.util.ArrayList;

public class User implements Comparable<User>, UserReport_ {

	public String name;
	public ArrayList<JobReport_> joblist;
	public int budget_consumed;
	public int last_job_end_time;
	public User(String name)
	{
		this.name = name;
		this.budget_consumed = 0;
		joblist = new ArrayList<>();
	}
    @Override
    public int compareTo(User user) 
    {
    	if(budget_consumed > user.budget_consumed)
        return -1;
    	else if(budget_consumed < user.budget_consumed)
    		return 1;
    	else
    	{
    		if(this.last_job_end_time == 0 && user.last_job_end_time == 0)
    			return 0;
    		if(this.last_job_end_time < user.last_job_end_time)
    			return -1;
    		else return 1; 
    	}
    }
    
    public String user()    { return name; }

    public int consumed() { return budget_consumed; }   

}
