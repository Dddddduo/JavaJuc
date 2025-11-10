import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    // 信号量为2 表示同时只能有2个线程访问
    private final Semaphore semaphore = new Semaphore(2);

    public void access() throws InterruptedException {
        // 尝试获取信号量 如果获取失败 则阻塞等待
        semaphore.acquire();
        try {
            // 访问共享资源
            System.out.println("线程 " + Thread.currentThread().getName() + " 正在访问共享资源");
            Thread.sleep(1000); // 模拟访问耗时
        } finally {
            // 释放信号量 通知其他线程可以获取信号量
            semaphore.release();
            System.out.println(Thread.currentThread().getName()+" "+"释放信号量");
        }
    }

    public static void main(String[] args) {
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    // 现在有10辆车 只有两个停车位 所以只有2辆车可以停在停车位上
                    semaphoreDemo.access();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    }


}