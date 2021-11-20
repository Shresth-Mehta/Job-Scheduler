# Job Scheduler

# 1. PriorityQueue Interface
- Priority queue node which stores the object entered, 
T value – object, int priority – the priority value
  ``` Java
  HeapNode<T extends Comparable> implements Comparable<HeapNode<T>>
  ```

- Return 0,1,-1 depending on the priority of the value entered and then comparing the insertion order
  ``` Java
  int compareTo(HeapNode<T> node)
  ```



# 2. MaxHeap Class
  ``` Java
  Class MaxHeap extends PriorityQueue interface
  ```

### 2.1 Methods:
- Inserting the object based on the priority
  ``` Java
  void insert(T element)
  ```

- Number of elements stored in the priority queue
  ``` Java
  int count()
  ```

- Return the highest priority element in the max-heap
  ``` Java
  T extractMax()
  ```




# 3. Student Class
  ``` Java
  Class Student implements comparable
  ```
### 3.1 Methods:
- Return the name of the student
  ``` Java
  String getName()
  ```
- Return the to be printed stuff as per the assignment requirement
  ``` Java
  String toString()
  ```


# 4. Person class:
- Constructor
  ``` Java
  Person person = new Person(String name,String phone_number)
  ```
- Return the name of the student
  ``` Java
  String getName()
  ```

- Return a string as per the assignment requirement
  ``` Java
  String toString()
  ```


# 5. Trie Class 
  ``` Java
  Class Trie<T> implements TrieInterface<T>
  ```
### 5.1 Methods:
- Delete the element stored with the given string and returns true if got deleted else, returns false
  ``` Java
  boolean delete(String word)
  ```
- Return the TrieNode with the given String as key
  ``` Java
  TrieNode search(String word)
  ```
- Return a new node with all properties set to null if the key is not found else return 1 node which can follow up on the given string
  ``` Java
  TrieNode<T> startsWith(String prefix)
  ```
- Print the trienode with sorted outputs
  ``` Java
  void printTrie(TrieNode trienode)-
  ```

- Insert the given value with the given key and returns a Boolean verifying the insertion to be true or false
  ``` Java
  Boolean insert(String word,T value)-
  ```
- Print all the items present at a particular level in a Trie
  ``` Java
  void printLevel(int Level)-
  ```
- Print the entire tree with all the levels separately marked
  ``` Java
  void print()
  ```


# 6. RedBlackTree Class
  ``` Java
  Class RBTree<T extends Comparable, E implements RBTreeInterface<T,E>
  ```
- Constructor
  ``` Java
  RBTree<T,E> constructor = new RBTree()
  ```
- Insert the given element with the given key
  ``` Java
  void insert(T key, E value)-
  ```
- Print the required node stored with the given key
  ``` Java
  RedBlackNode<T,E> search(T key)
  ```
