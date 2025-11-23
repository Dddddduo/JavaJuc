import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.生产者线程调用 produce() 方法。
 * 2.它首先获取 lock.lock()。
 * 3.它检查 buffer.size() == CAPACITY（缓冲区是否满了）。
 * 4.条件满足（缓冲区确实满了），于是它调用 notFull.await()。
 * 5.关键点：await() 方法内部，线程释放了 lock，然后进入 notFull 的等待队列，最后阻塞自己。
 * 6.此时，生产者线程暂停执行，CPU 可以切换到其他线程。
 * 7.消费者线程此时有机会获取到 lock，它消费掉一个数据，使缓冲区不再满。
 * 8.消费者消费完后，调用 notFull.signal()。
 * 9.signal() 方法会从 notFull 的等待队列中唤醒一个线程（也就是刚才阻塞的生产者线程）。
 * 10.线程被唤醒后，会尝试重新获取 lock。
 * 11.如果成功获取到锁，它就会从 await() 方法返回，继续执行后续的代码（也就是生产数据）。
 */
public class ConditionDemo {
    // 缓冲区（队列），容量为1
    private final Queue<Integer> buffer = new LinkedList<>();
    private static final int CAPACITY = 1;

    // 创建ReentrantLock
    private final ReentrantLock lock = new ReentrantLock();
    // 创建两个Condition：notEmpty（缓冲区非空）、notFull（缓冲区非满）
    private final Condition notEmpty;
    private final Condition notFull = lock.newCondition();

    public ConditionDemo() {
        notEmpty = lock.newCondition();
    }

    // 生产者线程：生产数据放入缓冲区
    public void produce(int data) throws InterruptedException {
        lock.lock(); // 获取锁
        try {
            // 缓冲区满了，生产者等待（释放锁，进入notFull等待队列）
            while (buffer.size() == CAPACITY) {
                System.out.println("缓冲区满了，生产者等待...");
                // 缓冲区满了,生产者等待
                // 1.当前线程释放锁
                // 2.线程进入notFull等待队列
                // 3.阻塞,等待消费者消费数据
                notFull.await();
            }

            // 生产数据放入缓冲区
            buffer.offer(data);
            System.out.println("生产者生产数据：" + data + "，缓冲区当前大小：" + buffer.size());

            // 唤醒消费者（缓冲区非空了，发送notEmpty信号）
            notEmpty.signal();
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    // 消费者线程：从缓冲区消费数据
    public int consume() throws InterruptedException {
        lock.lock(); // 获取锁
        try {
            // 缓冲区空了，消费者等待（释放锁，进入notEmpty等待队列）
            while (buffer.isEmpty()) {
                System.out.println("缓冲区空了，消费者等待...");
                // 缓冲区空了,消费者等待
                // 1.当前线程释放锁
                // 2.线程进入notEmpty等待队列
                // 3.阻塞,等待生产者生产数据
                notEmpty.await();
            }

            // 消费数据
            int data = buffer.poll();
            System.out.println("消费者消费数据：" + data + "，缓冲区当前大小：" + buffer.size());

            // 唤醒生产者（缓冲区非满了，发送notFull信号）
            notFull.signal();
            return data;
        } finally {
            lock.unlock(); // 释放锁
        }
    }

    public static void main(String[] args) {
        ConditionDemo pc = new ConditionDemo();

        // 生产者线程：生产1-5的数据
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    pc.produce(i);
                    Thread.sleep(1000); // 模拟生产耗时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "生产者").start();

        // 消费者线程：消费5次数据
        new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                try {
                    pc.consume();
                    Thread.sleep(1500); // 模拟消费耗时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "消费者").start();
    }
}
