import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class SafeAccessQueue {
    private Node front, rear;
    private final Lock lock = new ReentrantLock();

    public SafeAccessQueue() {
        front = rear = null;
    }

    public void enqueue(int data) {
        lock.lock();
        try {
            Node newNode = new Node(data);
            if (rear == null) {
                front = rear = newNode;
                return;
            }
            rear.next = newNode;
            rear = newNode;
        } finally {
            lock.unlock();
        }
    }

    public Integer dequeue() {
        lock.lock();
        try {
            if (front == null) {
                return null;
            }
            int data = front.data;
            front = front.next;
            if (front == null) {
                rear = null;
            }
            return data;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return front == null;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        SafeAccessQueue queue = new SafeAccessQueue();

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.isEmpty());
        System.out.println(queue.dequeue());
        System.out.println(queue.isEmpty());
    }
}