
// Practical approach :use HashMap to store the key-nodes pairs for fast access
import java.util.HashMap;

// Node for doubly linked list:
class Node {
    int key;
    int value;
    // previous and next in the linked list:
    Node previous;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

// implement the LRU Cache
class LRUCache {
    private int capacity;
    private HashMap<Integer, Node> cache;
    private Node head;
    private Node tail;

    // initialize:
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        head.next = tail;
        tail.previous = head;
    }

    // get
    // get the value if it exist:
    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            remove(node);
            add(node);
            return node.value;
        }
        return -1;
    }
    // put

    public void put(int key, int value) {
        // removing the old node: if it exists
        if (cache.containsKey(key)) {
            remove(cache.get(key));
        }
        Node node = new Node(key, value);
        add(node);
        cache.put(key, node);
        if (cache.size() > capacity) {
            Node lru = tail.previous;
            remove(lru);
            cache.remove(lru.key);
        }
    }

    // remove a node from doubly linked list:
    private void remove(Node node) {
        node.previous.next = node.next;
        node.next.previous = node.previous;
    }

    // add a node to doubly linked list:
    private void add(Node node) {
        node.next = head.next;
        node.previous = head;
        head.next.previous = node;
        head.next = node;
    }

    public static void main(String[] args) {
        // capacity 2
        LRUCache cache = new LRUCache(2);

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1)); // returns 1
        cache.put(3, 3); // evicts key 2
        System.out.println(cache.get(2)); // returns -1 (not found)
        cache.put(4, 4); // evicts key 1
        System.out.println(cache.get(1)); // returns -1 (not found)
        System.out.println(cache.get(3)); // returns 3
        System.out.println(cache.get(4)); // returns 4
    }
}
