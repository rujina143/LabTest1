import java.util.LinkedList;
import java.util.Random;

class Stack {
    private LinkedList<Integer> stack = new LinkedList<>();
    private final int MAX_SIZE = 10;

    public synchronized void push(int value) throws InterruptedException {
        while (stack.size() >= MAX_SIZE) {
            wait();
        }
        stack.add(value);
        notifyAll();
    }

    public synchronized int pop() throws InterruptedException {
        while (stack.isEmpty()) {
            wait();
        }
        int value = stack.removeLast();
        notifyAll();
        return value;
    }
}

class Producer implements Runnable {
    private Stack stack;
    private Random random = new Random();

    public Producer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                int value = random.nextInt(100) + 1;
                stack.push(value);
                System.out.println("Produced: " + value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

class Consumer implements Runnable {
    private Stack stack;

    public Consumer(Stack stack) {
        this.stack = stack;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                int value = stack.pop();
                System.out.println("Consumed: " + value);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class StackImplementation {
    public static void main(String[] args) {
        Stack stack = new Stack();
        Thread producerThread = new Thread(new Producer(stack));
        Thread consumerThread = new Thread(new Consumer(stack));

        producerThread.start();
        consumerThread.start();
    }
}