package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;

    public Student(String trim, int parseInt) {
    	this.name = trim;
    	this.marks = parseInt;
    }


    @Override
    public int compareTo(Student student) {
    	if(this.marks > student.marks)
    		return 1;
    	if(this.marks < student.marks)
    		return -1;
    	return 0;
    }

    public String getName() {
        return name;
    }
    
    public String toString()
    {
    	String ans = "Student{name='"+ this.name+"', marks="+this.marks+"}";
    	return ans;
    }
}
