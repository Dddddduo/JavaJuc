import java.util.concurrent.TimeUnit;

public class DeadLockDemo {

    // 展示死锁
    static Object lockA = new Object();
    static Object lockB = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "\t" + "自己持有A锁 期待获得B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockB) {
                    System.out.println(Thread.currentThread().getName() + "\t" + "在自己持有A锁的情况下 获取了B锁");
                }
            }
        }, "a").start();

        new Thread(() -> {
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t" + "自己持有A锁 期待获得B锁");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lockA) {
                    System.out.println(Thread.currentThread().getName() + "\t" + "在自己持有A锁的情况下 获取了B锁");
                }
            }
        }, "b").start();
    }

}