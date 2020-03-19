package ProjectManagement;
import java.util.ArrayList;

public class Project
{	
	public int priority;
	public String name;
	public int budget;
	public ArrayList<JobReport_> joblist;
	public Project(String name,int priority,int budget)
	{
		this.priority = priority;
		this.name = name;
		this.budget = budget;
		joblist = new ArrayList<>();
	}

}
