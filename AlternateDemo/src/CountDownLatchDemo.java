import java.util.concurrent.CountDownLatch;

// 这边用计数器实现的是一个先后打印
// await():阻塞
// countDown():计数器-1
public class CountDownLatchDemo {
    // 计数器=2：等待2个线程完成
    static CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 10; i += 2) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            countDownLatch.countDown(); // 线程1完成，计数器-1
        }, "线程1");

        Thread t2 = new Thread(() -> {
            for (int i = 2; i <= 10; i += 2) {
                System.out.println(Thread.currentThread().getName() + " " + i);
            }
            countDownLatch.countDown(); // 线程2完成，计数器-1
        }, "线程2");

        t1.start();
        t2.start();

        try {
            // 主线程等待t1和t2都完成（计数器=0）
            countDownLatch.await();
            System.out.println("所有线程执行完毕！");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}