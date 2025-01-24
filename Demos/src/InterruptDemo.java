import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class InterruptDemo {
    // 如何在多线程环境下优雅的退出某个线程

    // volatile修饰的变量
    static volatile boolean isStop = false;

    // 原子操作类 AtomicBoolean
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("程序结束");
                    break;
                }
                System.out.println("hello isStop");
            }
        }, "t1");
        t1.start();

        // 暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            // 修改t1线程的中断标志位为true
            t1.interrupt();
        },"t2").start();
    }

    public static void m1(String[] args) {
        // 通过一个volatile变量实现
        new Thread(() ->{
            while (true){
                if(isStop){
                    System.out.println("程序结束");
                    break;
                }
                System.out.println("hello isStop");
            }
        },"t1").start();

        // 暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // volatile变量具有可见性
        new Thread(() ->{
            isStop = true;
        },"t2").start();
    }

    public static void m2(String[] args) {
        // 通过原子操作类实现
        new Thread(() ->{
            while (true){
                if(atomicBoolean.get()){
                    System.out.println("程序结束");
                    break;
                }
                System.out.println("hello isStop");
            }
        },"t1").start();

        // 暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() ->{
            atomicBoolean.set(true);
        },"t2").start();
    }

}
