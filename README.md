# COL106: Project Management Assignment

# 1. PriorityQueue Interface
- Priority queue node which stores the object entered, 
T value – object, int priority – the priority value
  ``` C
  HeapNode<T extends Comparable> implements Comparable<HeapNode<T>>
  ```

- Return 0,1,-1 depending on the priority of the value entered and then comparing the insertion order
  ```
  int compareTo(HeapNode<T> node)
  ```



# 2. MaxHeap Class
  ```
  Class MaxHeap extends PriorityQueue interface
  ```

### 2.1 Methods:
- Inserting the object based on the priority
  ```
  insert(T element)
  ```

- Number of elements stored in the priority queue
  ```
  int count()
  ```

- Return the highest priority element in the max-heap
  ```
  extractMax()
  ```




# 3. Student Class
  ```
  Class Student implements comparable
  ```
### 3.1 Methods:
- Return the name of the student
  ```
  String getName()
  ```
- Return the to be printed stuff as per the assignment requirement
  ```
  String toString()
  ```


# 4. Person class:
- Constructor
  ```
  Person person = new Person(String name,String phone_number)
  ```
- Return the name of the student
  ```
  String getName()
  ```

- Return a string as per the assignment requirement
  ```
  String toString()
  ```


# 5. Trie Class 
  ```
  Class Trie<T> implements TrieInterface<T>
  ```
### 5.1 Methods:
- Delete the element stored with the given string and returns true if got deleted else, returns false
  ```
  boolean delete(String word)
  ```
- Return the TrieNode with the given String as key
  ```
  TrieNode search(String word)
  ```
- Return a new node with all properties set to null if the key is not found else return 1 node which can follow up on the given string
  ```
  TrieNode<T> startsWith(String prefix)
  ```
- Print the trienode with sorted outputs
  ```
  void printTrie(TrieNode trienode)-
  ```

- Insert the given value with the given key and returns a Boolean verifying the insertion to be true or false
  ```
  Boolean insert(String word,T value)-
  ```
- Print all the items present at a particular level in a Trie
  ```
  void printLevel(int Level)-
  ```
- Print the entire tree with all the levels separately marked
  ```
  void print()
  ```


# 6. RedBlackTree Class
  ```
  Class RBTree<T extends Comparable, E implements RBTreeInterface<T,E>
  ```
- Constructor
  ```
  RBTree<T,E> constructor = new RBTree()
  ```
- Insert the given element with the given key
  ```
  void insert(T key, E value)-
  ```
- Print the required node stored with the given key
  ```
  RedBlackNode<T,E> search(T key)
  ```
