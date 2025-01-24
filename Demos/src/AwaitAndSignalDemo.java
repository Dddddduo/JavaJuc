import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitAndSignalDemo {
    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();
    public static void main(String[] args) {

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
                condition.await();
                System.out.println(Thread.currentThread().getName()+"\t"+"---被唤醒");
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(() -> {
//            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
                condition.signal();
                System.out.println(Thread.currentThread().getName()+"\t"+"---发出通知");
            }finally {
//                lock.unlock();
            }
        },"t2").start();

    }
}
