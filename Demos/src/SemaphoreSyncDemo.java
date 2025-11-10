import java.util.concurrent.*;

// 用信号量模拟同步锁
public class SemaphoreSyncDemo {

    public static void main(String[] args) {
        myLock lock = new myLock();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    lock.lock();
                    System.out.println("线程 " + Thread.currentThread().getName() + " 正在访问共享资源");
                    Thread.sleep(1000); // 模拟访问耗时
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    lock.unlock();
                }
            }).start();
        }
    }

    public static class myLock {
        private final Semaphore semaphore = new Semaphore(1);
        // 获得锁
        private void lock() throws InterruptedException {
            semaphore.acquire();
        }
        // 释放锁
        private void unlock() {
            semaphore.release();
        }
    }

}
