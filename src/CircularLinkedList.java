public class CircularLinkedList {
    private Node head;
    private Node tail;
    private int size;

    /**
     * Class constructor that requires the number of cities to determine the number of bits needed in the bit string
     * @param numOfCities - The number of cities to input
     */
    public CircularLinkedList(int numOfCities) {
        this.head = null;
        this.tail = null;
        this.size = (int) Math.ceil(Math.log(numOfCities))*(numOfCities - 1);
        //Initialize the list to be correct size and assigned with all 0's (false)
        for (int i = 0; i < this.size; i++) {
            addNode(false);
        }

    }

    /**
     * Search the circular given an index number including negative
     * @param index - The input index
     * @return - Returns the value at the sought after index
     */
    public boolean search(int index) {
        if (index < 0) {
            index = (size + index % size) % size;
        } else {
            index = index % size;
        }

        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.nextNode;
        }

        return curr.value;
    }

    /**
     * Sets the value at the given index
     * @param index - The given index to set the value for
     * @param value - The value to assign to the given index
     */
    public void setValue(int index, boolean value) {
        if (index < 0) {
            index = (size + index % size) % size;
        } else {
            index = index % size;
        }

        Node curr = head;
        for (int i = 0; i < index; i++) {
            curr = curr.nextNode;
        }

        curr.value = value;
    }

    /**
     * Adding a node to the end of list
     * @param value - The value to be given to the new node in end of the list
     */
    public void addNode(boolean value) {
        Node newNode = new Node(value);

        if (head == null) {
            head = newNode;
        } else {
            tail.nextNode = newNode;
        }

        tail = newNode;
        tail.nextNode = head;
        size++;
    }


}

/**
 * Auxillary Node class
 */
class Node {
    boolean value;
    Node nextNode;

    public Node(boolean value) {
        this.value = value;
    }
}
