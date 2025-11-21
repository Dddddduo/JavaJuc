import java.util.*;

public class SyncConditionDemo {
    private final Queue<Integer> buffer = new LinkedList<>();
    private static final int CAPACITY = 1;

    public synchronized void produce(int data) throws InterruptedException {
        while (buffer.size() == CAPACITY) {
            System.out.println("生产者 " + Thread.currentThread().getName() + " 等待：缓冲区满");
            this.wait(); // 释放锁，进入等待
        }
        buffer.offer(data);
        System.out.println("生产者 " + Thread.currentThread().getName() + " 生产：" + data);
        this.notifyAll(); // 唤醒所有等待的线程
    }

    public synchronized int consume() throws InterruptedException {
        while (buffer.isEmpty()) {
            System.out.println("消费者 " + Thread.currentThread().getName() + " 等待：缓冲区空");
            this.wait(); // 释放锁，进入等待
        }
        int data = buffer.poll();
        System.out.println("消费者 " + Thread.currentThread().getName() + " 消费：" + data);
        this.notifyAll(); // 唤醒所有等待的线程
        return data;
    }

    public static void main(String[] args) {
        SyncConditionDemo pc = new SyncConditionDemo();

        // 2个生产者
        for (int i = 1; i <= 2; i++) {
            final int id = i;
            new Thread(() -> {
                for (int j = 1; j <= 3; j++) {
                    try {
                        pc.produce(id * 10 + j);
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "P" + i).start();
        }

        // 2个消费者
        for (int i = 1; i <= 2; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 3; j++) {
                    try {
                        pc.consume();
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "C" + i).start();
        }
    }
}