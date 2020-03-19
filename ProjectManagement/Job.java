package ProjectManagement;

public class Job implements Comparable<Job>,JobReport_{

	public Project project;
	public int runtime;
	public User user;
	public String name;
	public String status;
	public int end_time;
	public int insert_time;
	//public int j_priority;
	public int insert_priority;
	public Job(String name,Project project,User user,int runtime)
	{
		this.project = project;
		this.user = user;
		this.name = name;
		this.runtime = runtime;
		this.end_time = 0;
		//this.j_priority = project.priority;
		this.status = "REQUESTED";
	}
	
    @Override
    public int compareTo(Job job) {
        if(this.project.priority > job.project.priority)
        	return 1;
        if(this.project.priority < job.project.priority)
        	return -1;
        if(this.insert_priority < job.insert_priority)
        	return 1;
        if(this.insert_priority > job.insert_priority)
        	return -1;
    	return 0;
    }
    public String user()
    {
    	return user.name; 
    }

    public String project_name()  { return project.name; }

    public int budget()  { return project.budget; }

    public int arrival_time()  { return insert_time; }

    public int completion_time() { return end_time; }
    
    public int priority()
    {
    	return this.project.priority;
    }
    @Override
    public String toString()
    {
		String s = "Job{user='"+this.user.name+"', project='"+this.project.name+"', jobstatus="+this.status+", execution_time="+this.runtime+", start_time="+this.insert_time+", end_time="+this.end_time+", name='"+this.name+"'}";
    	return s;
    }
    
}