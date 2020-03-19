package ProjectManagement;
// correct the search for user heap
//build the sort function

import java.io.*;

import java.net.URL;
import java.util.ArrayList;
import PriorityQueue.*;
import RedBlack.*;
import trie_2.*;

public class Scheduler_Driver extends Thread implements SchedulerInterface {

	int p_count = 0;
	Trie<Project> p_trie = new Trie<>(); // resolved
	Trie<User> u_trie = new Trie<>();	// resolved
	Trie<Job> j_trie = new Trie<>();	// resolved
	MaxHeap<Job> heap = new MaxHeap<>();	// resolved
	MaxHeap<Job> incomplete_jobs = new MaxHeap<>();	//being used during stats
	RBTree<String,Job> rbt_deleted_jobs = new RBTree<>();	// resolved
	ArrayList<String> completed_jobs = new ArrayList<>();	// resolved change to queue
	ArrayList<Job> incomplete_jobs_list = new ArrayList<>();	// think of another ds
	public int global_time = 0;
	//User_Heap top_consumers = new User_Heap();	// resolved
	//Trie<User> top_consumers_search = new Trie<>();	// resolved
	ArrayList<User> top_consumers = new ArrayList<>();
    public static void main(String[] args) throws IOException {
//

        Scheduler_Driver scheduler_driver = new Scheduler_Driver();
        File file;
        if (args.length == 0) {
            URL url = Scheduler_Driver.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File commandFile) throws IOException {


        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(commandFile));

            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String project_name, user_name;
                Integer start_time, end_time;

                long qstart_time, qend_time;

                switch (cmd[0]) {
                    case "PROJECT":
                        handle_project(cmd);
                        break;
                    case "JOB":
                        handle_job(cmd);
                        break;
                    case "USER":
                        handle_user(cmd[1]);
                        break;
                    case "QUERY":
                        handle_query(cmd[1]);
                        break;
                    case "": // HANDLE EMPTY LINE
                        handle_empty_line();
                        break;
                    case "ADD":
                        handle_add(cmd);
                        break;
                    //--------- New Queries
                    case "NEW_PROJECT":
                    case "NEW_USER":
                    case "NEW_PROJECTUSER":
                    case "NEW_PRIORITY":
                        timed_report(cmd);
                        break;
                    case "NEW_TOP":
                    	System.out.println("Top query");
                        qstart_time = System.nanoTime();
                        timed_top_consumer(Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    case "NEW_FLUSH":
                    	System.out.println("Flush query");
                        qstart_time = System.nanoTime();
                        timed_flush( Integer.parseInt(cmd[1]));
                        qend_time = System.nanoTime();
                        System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                        break;
                    default:
                        System.err.println("Unknown command: " + cmd[0]);
                }

            }


            run_to_completion();
            print_stats();

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + commandFile.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }

    @Override
    public ArrayList<JobReport_> timed_report(String[] cmd) {
        long qstart_time, qend_time;
        ArrayList<JobReport_> res = null;
        switch (cmd[0]) {
            case "NEW_PROJECT":
            	System.out.println("Project query");
                qstart_time = System.nanoTime();
                res = handle_new_project(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_USER":
            	System.out.println("User query");
                qstart_time = System.nanoTime();
                res = handle_new_user(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));

                break;
            case "NEW_PROJECTUSER":
            	System.out.println("Project User query");
                qstart_time = System.nanoTime();
                res = handle_new_projectuser(cmd);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
            case "NEW_PRIORITY":
            	System.out.println("Priority query");
                qstart_time = System.nanoTime();
                res = handle_new_priority(cmd[1]);
                qend_time = System.nanoTime();
                System.out.println("Time elapsed (ns): " + (qend_time - qstart_time));
                break;
        }

        return res;
    }

    @Override
    public ArrayList<UserReport_> timed_top_consumer(int top) 
    {
    	ArrayList<UserReport_> ans = new ArrayList<>();
    	if(top >= top_consumers.size())
    		top = top_consumers.size(); 
    	for(int i = 0;i<top;i++)
    	{
    		
    		ans.add(top_consumers.get(i));
    	}
    	
    	ans.sort(null);
    	
    	
    	
    	return ans;
    }



    @Override
    public void timed_flush(int waittime) 
    {
    	
    	ArrayList<Job> retrieval_queue = new ArrayList<>();
    	int len = heap.count();
    	int flush_global_time = global_time;
    	for(int i =0;i<len;i++)
    	{
    		Job job = heap.extractMax();
    		int j_w_t = flush_global_time - job.insert_time;
    		if(j_w_t >= waittime && job.project.budget>=job.runtime)
    		{
    			global_time += job.runtime;
    			job.project.budget -= job.runtime;
    			job.end_time = global_time;
    			job.status = "COMPLETED";
    			job.user.last_job_end_time = job.end_time;
    			String s = "Job{user='" + job.user.name + "', project='" + job.project.name + "', jobstatus="
						+ job.status + ", execution_time=" + job.runtime + ", end_time=" + job.end_time + ", name='"
						+ job.name + "'}";    			
    			completed_jobs.add(s);

    				incomplete_jobs_list.remove(job);
    			job.user.budget_consumed += job.runtime;
    			
    		}
    		else 
    			retrieval_queue.add(job);
    	}
    	for(Job jb:retrieval_queue)
    	{
    		heap.insert(jb);
    	}
    	
    	
    	/*ArrayList<Job> execute_jobs = new ArrayList<>();
    	int flush_global_time = global_time;
    	
    	for(HeapNode<Job> node: heap.heap)
    	{
    		Job job = node.value;
    		int j_w_t = flush_global_time - job.insert_time;
    		if(j_w_t >= waittime && job.project.budget>=job.runtime)
    			execute_jobs.add(job);
    	}
    	execute_jobs.sort(null);
    	int size = execute_jobs.size();
    	for(int a = size-1;a>0;a--)
    	{
    		Job job = execute_jobs.get(a); 
    		if(job.project.budget >=job.runtime)
    		{
    			global_time += job.runtime;
    			job.project.budget -= job.runtime;
    			job.end_time = global_time;
    			job.status = "COMPLETED";
    			job.user.last_job_end_time = job.end_time;
    			String s = "Job{user='" + job.user.name + "', project='" + job.project.name + "', jobstatus="
						+ job.status + ", execution_time=" + job.runtime + ", end_time=" + job.end_time + ", name='"
						+ job.name + "'}";    			
    			completed_jobs.add(s);

    				incomplete_jobs_list.remove(job);
    			job.user.budget_consumed += job.runtime;
    		}
    	}
    	*/
    }
   
    
    private ArrayList<JobReport_> handle_new_priority(String s) 
    {
    	int n = Integer.parseInt(s);
    	ArrayList<JobReport_> list = new ArrayList<>();
    	for(Job job: incomplete_jobs_list)
    	{
    		if(job.priority() >= n)
    		{
    			list.add(job);
    		}
    	}	
        return list;
    }

    private ArrayList<JobReport_> handle_new_projectuser(String[] cmd) 
    {
    	int t1 = Integer.parseInt(cmd[3]);
    	int t2 = Integer.parseInt(cmd[4]);
    	Node<User> user = u_trie.search(cmd[2]);
    	if(user == null)
    		return null;
    	Node<Project> project =p_trie.search(cmd[1]);
    	if(project == null)
    		return null;
    	String name = project.value.name;
    	ArrayList<JobReport_> list = user.value.joblist;
    	ArrayList<JobReport_> ans = new ArrayList<>();
    	for(JobReport_ job:list)
		{
			int time = job.arrival_time();
			if(job.project_name().compareTo(name) == 0 && t1<= time && time <=t2)
			{
				ans.add(job);
			}
		}
		if(ans.isEmpty())
		return ans;
		ans.sort(new CompareJobReport());	
    	return ans;

    }

    private ArrayList<JobReport_> handle_new_user(String[] cmd) 
    {
    	// these need not be sorted
    	int t1 = Integer.parseInt(cmd[2]);
    	int t2 = Integer.parseInt(cmd[3]);
    	Node<User> user = u_trie.search(cmd[1]);
    	if(user == null)
    	return null;
    	else
    	{
    		ArrayList<JobReport_> list = user.value.joblist;
    		ArrayList<JobReport_> ans = new ArrayList<>();
    		for(JobReport_ job:list)
    		{
    			int time = job.arrival_time();
    			if(time > t2)
    				break;
    			if(t1<= time && time<=t2)
    			{
    				ans.add(job);
    			}
    		}
//    		if(ans.isEmpty())
//    		return ans;
    		//ans.sort(new CompareJobReport());	
    		//for checking
        	
    		return ans;
    		
    	}
    }

    private ArrayList<JobReport_> handle_new_project(String[] cmd) 
    {
    	// need not be sorted
    	int t1 = Integer.parseInt(cmd[2]);
    	int t2 = Integer.parseInt(cmd[3]);
    	Node<Project> project = p_trie.search(cmd[1]);
    	if(project == null)
        return null;
    	else
    	{
    		ArrayList<JobReport_> list = project.value.joblist;
    		ArrayList<JobReport_> ans = new ArrayList<>();
    		for(JobReport_ job:list)
    		{
    			int time = job.arrival_time();
    			if(time > t2)
    				break;
    			if(t1<= time && time <=t2)
    			{
    				ans.add(job);
    			}
    		}
//    		if(ans.isEmpty())
//    		return ans;
    		//ans.sort(new CompareJobReport());	
    		
    		
    		return ans;
    	}
    }
    

    public void schedule() {
            execute_a_job();
    }

    public void run_to_completion()
    {
    	while(heap.count() > 0 ) {
			System.out.println("Running code");
			System.out.println("Remaining jobs: " + heap.count());
			while (true) {
				Job job = heap.extractMax();
				System.out.println("Executing: " + job.name + " from: " + job.project.name);
				if (job.runtime <= job.project.budget && job.status.compareTo("REQUESTED") == 0) {
					global_time += job.runtime;
					job.end_time = global_time;
					job.project.budget -= job.runtime;
					System.out.println("Project: " + job.project.name + " budget remaining: " + job.project.budget);
					System.out.println("System execution completed");
					job.status = "COMPLETED";

					String s = "Job{user='" + job.user.name + "', project='" + job.project.name + "', jobstatus="
							+ job.status + ", execution_time=" + job.runtime + ", end_time=" + job.end_time + ", name='"
							+ job.name + "'}";
					completed_jobs.add(s);
					//if (index != -1)
						incomplete_jobs_list.remove(job);
					//System.out.println("yes");
					break;
				}

				// add job to tree
				job.status = "REQUESTED";
//				if (incomplete_jobs_list.indexOf(job) == -1)
//					incomplete_jobs_list.add(job);
				rbt_deleted_jobs.insert(job.project.name, job);
				System.out.println("Un-sufficient budget.");
				if (heap.count() == 0) {
					//System.out.println("Execution cycle completed");
					System.out.println("System execution completed");
					break;
				}

			} 
		}
    }
    
    private void getback()
    {
    	for(Job jobs:incomplete_jobs_list)
    	{
    		incomplete_jobs.insert(jobs);
    	}
    }
    public void print_stats() 
    {
    	System.out.println("--------------STATS---------------");
    	System.out.println("Total jobs done: "+completed_jobs.size());
    	int limit = completed_jobs.size();
//    	int i = 0;
//    	while(i<limit)
//    	{
//    		//System.out.println(i);
//    		System.out.println(completed_jobs.remove(i));
//    		i++;
//    	}
    	for(int i = 0;i<limit;i++)
    	{
    		System.out.println(completed_jobs.get(i));
    	}
    	System.out.println("------------------------");
    	System.out.println("Unfinished jobs: ");
    	getback();
    	int new_limit = incomplete_jobs.count();
    	for(int j = 0;j<new_limit;j++)
    	{
    		Job jb = incomplete_jobs.extractMax();
    		if(jb.end_time != 0)
    			System.out.println("Job{user='"+jb.user.name+"', project='"+jb.project.name+"', jobstatus="+jb.status+", execution_time="+jb.runtime+", end_time="+jb.end_time+", name='"+jb.name+"'}");
    		else
    			System.out.println("Job{user='"+jb.user.name+"', project='"+jb.project.name+"', jobstatus="+jb.status+", execution_time="+jb.runtime+", end_time=null, name='"+jb.name+"'}");

    	}
    	System.out.println("Total unfinished jobs: "+new_limit);
    	System.out.println("--------------STATS DONE---------------");
    	
    }

    public void handle_add(String[] cmd)
    {
    	
    	
    	System.out.println("ADDING Budget");
    	Project project = p_trie.search(cmd[1]).getValue();
    	if(project == null)
    	{
    		System.out.println("No such project exists. "+cmd[1]);
    		return ;
    	}
    	project.budget += Integer.parseInt(cmd[2]);
    	
    	ArrayList<Job> j_arr = rbt_deleted_jobs.search(cmd[1]).getValues();				// Here complexity can be improved as we are adding array into heap
    	
    	if(j_arr != null)
    	{
    		for(Job temp:j_arr)
    		{
    			heap.insert(temp);
    		}
    		j_arr.clear();   // note
    		
    	}
    }

    public void handle_empty_line() 
    {
       schedule();
    }


    public void handle_query(String key)
    {
    	System.out.println("Querying");
    	 
    	Node<Job> t = j_trie.search(key);
    	if(t == null)
    		System.out.println(key+": NO SUCH JOB");
    	else if(t.getValue().status.compareTo("REQUESTED") == 0)
    		System.out.println(key+": NOT FINISHED");
    	else if(t.getValue().status.compareTo("COMPLETED") == 0)
    		System.out.println(key+": COMPLETED");
    }

    public void handle_user(String name) 
    {
    	System.out.println("Creating user");
    	Node<User> t = u_trie.search(name);
    	if(t != null)
    	{
    		System.out.println("User already exists: "+name);
    		return ;
    	}
    	User user = new User(name);
    	u_trie.insert(name,user);
    	top_consumers.add(user); // added consumers here
    	
    	
    }

    public void handle_job(String[] cmd) 
    {
    	System.out.println("Creating job");
    	Project project;
    	User user;
    	Node<Project> t = p_trie.search(cmd[2]);
    	//project = p_trie.search(cmd[2]).getValue();
    	if(t == null)
    	{
    		System.out.println("No such project exists. "+cmd[2]);
    		return ;
    	}
    	Node<User> t2 = u_trie.search(cmd[3]);
    	//user = u_trie.search(cmd[3]).getValue();
    	if(t2 == null)
    	{
    		System.out.println("No such user exists: "+cmd[3]);
    		return ;
    	}
    	project = t.getValue();
    	user = t2.getValue();
    	//System.out.println();
    	//System.out.println(project.name+" "+user.name);
    	//System.out.println();
    	Job job = new Job(cmd[1],project,user,Integer.parseInt(cmd[4]));
    	user.joblist.add(job);
    	project.joblist.add(job);
    	incomplete_jobs_list.add(job); // 										changed
    	job.status = "REQUESTED";
    	job.insert_time = global_time;
    	job.insert_priority = p_count;
    	p_count +=1;
    	heap.insert(job);
    	j_trie.insert(job.name, job);
    }

    public void handle_project(String[] cmd) 
    {
    	System.out.println("Creating project");
    	Project project = new Project(cmd[1],Integer.parseInt(cmd[2]),Integer.parseInt(cmd[3]));
    	p_trie.insert(cmd[1], project);
    }
    public void execute_a_job() 
    {
    	System.out.println("Running code");
    	System.out.println("Remaining jobs: "+heap.count());
    	if(heap.count() == 0)
    	{
    		System.out.println("Execution cycle completed");
    		return ;
    	}
    		
    	while(true)
    	{
    		Job job = heap.extractMax();
    		System.out.println("Executing: "+job.name+" from: "+job.project.name);
    		if(job.runtime <= job.project.budget && job.status.compareTo("REQUESTED") == 0)
    		{
    			global_time += job.runtime;
    			job.end_time = global_time;
    			job.project.budget -= job.runtime;
    			System.out.println("Project: "+job.project.name+" budget remaining: "+job.project.budget);
    			System.out.println("Execution cycle completed");
    			job.status = "COMPLETED";
    			job.user.last_job_end_time = job.end_time;

    			String s = "Job{user='"+job.user.name+"', project='"+job.project.name+"', jobstatus="+job.status+", execution_time="+job.runtime+", end_time="+job.end_time+", name='"+job.name+"'}";
    			completed_jobs.add(s);
    			int index = incomplete_jobs_list.indexOf(job);
    			//if(index != -1)															changed
    				incomplete_jobs_list.remove(index);
    			job.user.budget_consumed += job.runtime;
    			//Node<User> user = top_consumers_search.search(job.user());
    			
    			//top_consumers.sort(null);
    			// call the heapify function.
    			//System.out.println("yes");
    			break;
    		}
    		
    		// add job to tree
    		job.status = "REQUESTED";
//    		if(incomplete_jobs_list.indexOf(job) == -1)
//    			incomplete_jobs_list.add(job);
    		rbt_deleted_jobs.insert(job.project.name, job);
    		System.out.println("Un-sufficient budget.");
    		if(heap.count() == 0)
    		{
    			System.out.println("Execution cycle completed");
    			break;
    		}
    		
    	}
    
    }
    
    public void timed_handle_user(String name)
    {
    	Node<User> t = u_trie.search(name);
    	if(t != null)
    	{
    	
    		return ;
    	}
    	User user = new User(name);
    	u_trie.insert(name,user);
    	top_consumers.add(user); // added consumers here
    
    }
    
    public void timed_handle_job(String[] cmd)
    {
    	Project project;
    	User user;
    	Node<Project> t = p_trie.search(cmd[2]);
    	if(t == null)
    	{
    		
    		return ;
    	}
    	Node<User> t2 = u_trie.search(cmd[3]);
    	if(t2 == null)
    	{
    		
    		return ;
    	}
    	project = t.getValue();
    	user = t2.getValue();
    	Job job = new Job(cmd[1],project,user,Integer.parseInt(cmd[4]));
    	user.joblist.add(job);
    	project.joblist.add(job);
    	incomplete_jobs_list.add(job);
    	job.status = "REQUESTED";
    	job.insert_time = global_time;
    	job.insert_priority = p_count;
    	p_count +=1;
    	heap.insert(job);
    	j_trie.insert(job.name, job);
    }
    public void timed_handle_project(String[] cmd)
    {
    	Project project = new Project(cmd[1],Integer.parseInt(cmd[2]),Integer.parseInt(cmd[3]));
    	p_trie.insert(cmd[1], project);
    }

    public void timed_run_to_completion()
    {
    	while(heap.count() > 0 ) {
			while (true) {
				Job job = heap.extractMax();
				if (job.runtime <= job.project.budget && job.status.compareTo("REQUESTED") == 0) {
					global_time += job.runtime;
					job.end_time = global_time;
					job.project.budget -= job.runtime;
					job.status = "COMPLETED";

					String s = "Job{user='" + job.user.name + "', project='" + job.project.name + "', jobstatus="
							+ job.status + ", execution_time=" + job.runtime + ", end_time=" + job.end_time + ", name='"
							+ job.name + "'}";
					completed_jobs.add(s);
//					int index = incomplete_jobs_list.indexOf(job);
					//if (index != -1)
						incomplete_jobs_list.remove(job);
					break;
				}

				// add job to tree
				job.status = "REQUESTED";
//				if (incomplete_jobs_list.indexOf(job) == -1)
//					incomplete_jobs_list.add(job);
				rbt_deleted_jobs.insert(job.project.name, job);
				if (heap.count() == 0) {
					break;
				}

			} 
		}
    }
}

// check for top consumers - cihir's doubt
