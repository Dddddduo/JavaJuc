import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

// 这边的condition1和condition2共享一把lock锁
// await是阻塞,释放锁,同时进入自己的等待区
// signal是去等待区唤醒对应的线程
public class ReentDemo {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition condition1 = lock.newCondition();
    private static final Condition condition2 = lock.newCondition();
    private static  boolean flag = false;
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            lock.lock();
            for (int i = 1; i <= 10; i+=2) {
                while(flag==true){
                    try {
                        condition1.await();
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                    }
                }
                flag =true;
                System.out.println(Thread.currentThread().getName()+" "+i);
                condition2.signal();
            }
            lock.unlock();
        },"线程1");
        Thread t2 = new Thread(()->{
            lock.lock();
            for (int i = 2; i <= 10; i+=2) {
                while(flag==false){
                    try {
                        // 阻塞 并且会释放锁
                        condition2.await();
                    }catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                flag=false;
                System.out.println(Thread.currentThread().getName()+" "+i);
                condition1.signal();
            }
            lock.unlock();
        },"线程2");
        t1.start();
        t2.start();
    }

}
