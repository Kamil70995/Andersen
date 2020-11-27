import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AndersenMultithreadingProject {

    private static ThreadPoolExecutor executor;

    public AndersenMultithreadingProject() {

    }
    private static void loading(int number) throws InterruptedException {
        System.out.println("Truck #" + number + " is empty");

        Thread.sleep(100 + (int) (Math.random() * 300)); //время загрузки/выгрузки
        System.out.println("Truck #" + number + " has loaded the stuff at platform " + (Thread.currentThread().getId() - 10));
        System.out.println();
    }

    private static void unloading(int number) throws InterruptedException {

        System.out.println("Truck #" + number + " is full");
        Thread.sleep(100 + (int) (Math.random() * 300)); //время загрузки/выгрузки
        System.out.println("Truck #" + number + " has unloaded the stuff at platform " + (Thread.currentThread().getId()-10));
        System.out.println();
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
        for (int i = 1; i <= 10; i++) {
             int localid = i;
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    double a = Math.random()*100;
                    if (a > 50) {
                        try {
                            loading(localid);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        try {
                            unloading(localid);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            queue.add(runnable);
        }
        executor = new ThreadPoolExecutor(2, 2, 500,
                TimeUnit.MILLISECONDS, queue);
        executor.prestartAllCoreThreads();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

    }
}
