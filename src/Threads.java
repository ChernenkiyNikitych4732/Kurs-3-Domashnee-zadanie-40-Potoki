import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import javax.swing.plaf.IconUIResource;
import static java.lang.Thread.sleep;

public class Threads {

    public Integer count = 0;

    public Object flag = new Object();
    public static void main(String[] args) {
        Threads main = new Threads();

 //     main.doOperation(0);

        Thread demon = main.startDemon();

        main.doOperation(1);

        Thread thread = new Thread(() -> {
            main.doOperation(2);

            if (Thread.interrupted()) {
                throw new RuntimeException();
            }
            main.doOperation(3);

            if (!Thread.interrupted()) {
                main.doOperation(4);
            }
        });
        thread.start();

        new Thread(() -> {
            main.doOperation(5);
            main.doOperation(6);
            main.doOperation(7);
        }).start();

//        thread.interrupt();

        main.doOperation(8);
        main.doOperation(9);
        main.doOperation(10);

//      demon.interrupt();
    }

    public void doOperation(int number) {
        synchronized (flag) {
            System.out.println("Operation " + number + " Count " + count);
            count++;
        }

        String s = "";
        for (int i = 0; i < 100000; i++) {
            s += i;
        }
        count++;
    }

    public Thread startDemon() {
        Thread demon = new Thread(() -> {
            try {
                while (true) {
                    System.out.println("Demon running");
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                System.out.println("Demon interrupted");
            }
        });
        demon.start();
        return demon;
    }
}