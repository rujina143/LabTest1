import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ResourcePool<Test> {
    private final BlockingQueue<Test> resources;

    public ResourcePool(int size, Test resource) {
        resources = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            resources.add(resource);
        }
    }

    public Test acquire() throws InterruptedException {
        return resources.take();
    }

    public void release(Test resource) {
        resources.offer(resource);
    }

    public static void main(String[] args) {
        ResourcePool<String> pool = new ResourcePool<>(5, "Resource");

        Runnable task = () -> {
            try {
                String resource = pool.acquire();
                System.out.println(Thread.currentThread().getName() + " acquired " + resource);
                Thread.sleep(1000); // Simulate work
                pool.release(resource);
                System.out.println(Thread.currentThread().getName() + " released " + resource);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        for (int i = 0; i < 15; i++) {
            new Thread(task).start();
        }
    }
}