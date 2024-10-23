import java.util.ArrayList;

public class ArrayListCorruption {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Integer> list = new ArrayList<>();

        Runnable addElement = () -> {
            for (int i = 0; i < 100; i++) {
                list.add(i);
            }
        };

        Runnable removeElement = () -> {
            for (int i = 0; i < 25; i++) {
                if (!list.isEmpty()) {
                    list.remove(0);
                }
            }
        };

        Thread adder = new Thread(addElement);
        Thread remover = new Thread(removeElement);

        adder.start();
        remover.start();

        try {
            adder.join();
            remover.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list);
        System.out.println("Final size of the list: " + list.size());
    }
}
