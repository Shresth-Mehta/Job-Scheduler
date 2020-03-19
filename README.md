# COL106: Project Management Assignment

//PriorityQueue
HeapNode<T extends Comparable> implements Comparable<HeapNode<T>>
// priority queue node which stores the object entered
// T value – object, int priority – the priority value
//int compareTo(HeapNode<T> node)
//return 0,1,-1 depending on the priority of the value entered and then comparing the insertion order.
//MaxHeap
Class MaxHeap extends PriorityQueue interface
//insert(T element)-
Inserting the object based on the priority
//int count()-
Number of elements stored in the priority queue
//HeapNode extractMax()-
Returns the highest priority element in the max-heap
Class Student implements comparable
//String getName()-
Returns the name of the student
//String toString()-
Returns the to be printed stuff as per the assignment requirement
//Trie
Class Person
//Person person = new Person(String name,String phone_number)
//String getName()-
Returns the name of the student
//String toString()-
Returns a string as per the assignment requirement
Class Trie<T> implements TrieInterface<T>
//boolean delete(String word)-
Deletes the element stored with the given string and returns true if got deleted else, returns false.
//TrieNode search(String word)-
Returns the TrieNode with the given String as key.
Returns a new node with wall properties set to null if the key is not found
//TrieNode<T> startsWith(String prefix)-
Returns 1 node which can follow up on the given string
//void printTrie(TrieNode trienode)-
Prints the trienode with sorted outputs.
//Boolean insert(String word,T value)-
Inserts the given value with the given key and returns a Boolean verifying the insertion to be true or false.
//void printLevel(int Level)-
Prints all the items present at a particular level in a Trie.
//void print()-
Prints the entire tree with all the levels separately marked.
//RedBlackTree
Class RBTree<T extends Comparable, E implements RBTreeInterface<T,E>
//RBTree<T,E> constructor = new RBTree()
//void insert(T key, E value)-
Inserts the given element with the given key
//RedBlackNode<T,E> search(T key)-
Prints the required node stored with the given key
