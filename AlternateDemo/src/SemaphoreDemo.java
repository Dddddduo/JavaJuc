import java.util.concurrent.Semaphore;

// 信号量
// acquire()方法是拿一个通行证
// release()方法是释放一个通行证
public class SemaphoreDemo {
    public static final Semaphore semaphore1 = new Semaphore(1);
    public static final Semaphore semaphore2 = new Semaphore(0);

    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            try{
                for (int i = 1; i <= 10; i+=2) {
                    // 拿一个通行证
                    semaphore1.acquire();
                    System.out.println(Thread.currentThread().getName()+" "+i);
                    // 释放一个通行证
                    semaphore2.release();
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        },"线程1");

        Thread t2 = new Thread(()->{
            try{
                for (int i = 2; i <= 10; i+=2) {
                    // 拿一个通行证
                    semaphore2.acquire();
                    System.out.println(Thread.currentThread().getName()+" "+i);
                    // 释放一个通行证
                    semaphore1.release();
                }
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        },"线程2");

        t1.start();
        t2.start();
    }
}
