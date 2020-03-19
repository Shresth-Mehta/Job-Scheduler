package trie_2;

public class Person {

	String name;
	String ph_no;
    public Person(String name, String phone_number) {
    	this.name = name;
    	this.ph_no = phone_number;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString()
    {
    	return "[Name: "+this.name+", Phone="+this.ph_no+"]";
    }
}
