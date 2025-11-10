import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo2 {

    public static void main(String[] args) throws InterruptedException {

        final Semaphore semaphore = new Semaphore(2);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+" is running");
                    // 获取一个许可
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+" get the permits");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放一个许可
                    semaphore.release();
                }
                System.out.println(Thread.currentThread().getName()+" finish");
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+" is running");
                    // 获取一个许可
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+" get the permits");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放一个许可
                    semaphore.release();
                }
                System.out.println(Thread.currentThread().getName()+" finish");
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName()+" is running");
                    // 获取一个许可
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+" get the permits");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放一个许可
                    semaphore.release();
                }
                System.out.println(Thread.currentThread().getName()+" finish");
            }
        });

        t1.start();
        TimeUnit.MILLISECONDS.sleep(50);
        t2.start();
        // 进行休眠，确保T1、T2获取到许可
        TimeUnit.MILLISECONDS.sleep(50);
        t3.start();
        // 进行休眠，确保T3执行到acquire方法
        TimeUnit.MILLISECONDS.sleep(50);
        // 将T3线程进行打断
        t3.interrupt();
    }
}
