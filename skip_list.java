import java.util.Random;

class Node {
    int value;
    Node[] next;

    public Node(int value, int level) {
        this.value = value;
        this.next = new Node[level + 1];
    }
}

public class SkipList {
    private static final int MAX_LEVEL = 16; // Maximum level of the skip list
    private int level; // Current level of the skip list
    private Node head; // Head of the skip list
    private Random random; // Random number generator for determining node levels

    public SkipList() {
        this.level = 0;
        this.head = new Node(Integer.MIN_VALUE, MAX_LEVEL);
        this.random = new Random();
    }

    // Generate a random level for a new node
    private int randomLevel() {
        int level = 0;
        while (level < MAX_LEVEL && random.nextDouble() < 0.5) {
            level++;
        }
        return level;
    }

    // Insert a value into the skip list
    public void insert(int value) {
        Node[] update = new Node[MAX_LEVEL + 1];
        Node current = head;

        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value < value) {
                current = current.next[i];
            }
            update[i] = current;
        }

        int newLevel = randomLevel();
        if (newLevel > level) {
            for (int i = level + 1; i <= newLevel; i++) {
                update[i] = head;
            }
            level = newLevel;
        }

        Node newNode = new Node(value, newLevel);
        for (int i = 0; i <= newLevel; i++) {
            newNode.next[i] = update[i].next[i];
            update[i].next[i] = newNode;
        }
    }

    // Search for a value in the skip list
    public boolean search(int value) {
        Node current = head;
        for (int i = level; i >= 0; i--) {
            while (current.next[i] != null && current.next[i].value < value) {
                current = current.next[i];
            }
        }

        // Check if the value is found at the lowest level
        return current.next[0] != null && current.next[0].value == value;
    }

    // Display the skip list (for debugging purposes)
    public void display() {
        for (int i = level; i >= 0; i--) {
            Node current = head;
            while (current.next[i] != null) {
                System.out.print(current.next[i].value + " ");
                current = current.next[i];
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();

        skipList.insert(3);
        skipList.insert(6);
        skipList.insert(7);
        skipList.insert(9);
        skipList.insert(12);
        skipList.insert(19);
        skipList.insert(17);
        skipList.insert(26);
        skipList.insert(21);
        skipList.insert(25);
        
        skipList.display();

        int searchValue = 17;
        if (skipList.search(searchValue)) {
            System.out.println("Value " + searchValue + " found in the skip list.");
        } else {
            System.out.println("Value " + searchValue + " not found in the skip list.");
        }
    }
}
