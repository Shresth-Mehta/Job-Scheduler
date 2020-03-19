package trie_2;
public class Test 
{
	public static void main(String[] args)
	{
		Trie<Integer> data = new Trie<>();
		data.insert("s", 1);
		data.insert("shresth mehta", 2);
		data.insert("jhakkubhai", 3);
		data.insert("jhakku", 4);
		Boolean a = data.insert("suyash", 5);
		Boolean b = data.delete("jhakkubhai");
		System.out.println(data.search("jhakku").value.toString());
//		System.out.println(a.toString());
//		Boolean b = data.delete("shresth meh");
//		System.out.println(b.toString());
//		Node<Integer> node = data.search("shresth");
//		if(node == null)
//			System.out.println("wrong");
//		else if(node.value == null)
//			System.out.println("right");
//		System.out.println(data.search("shresth mehta").value.toString());
//		System.out.println(data.search("jhakkubhai").value.toString());
//		System.out.println(data.search("jhakku").value.toString());
//		System.out.println(data.search("suyash").value.toString());
		//System.out.println(data.search("shresth").value.toString());
		//data.print(13);
		data.print();
	}
}