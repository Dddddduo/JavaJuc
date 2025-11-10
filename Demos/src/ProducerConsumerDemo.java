import java.util.concurrent.Semaphore;
import java.util.Random;

public class ProducerConsumerDemo {

    // 缓冲区大小
    private static final int BUFFER_SIZE = 5;
    // 总生产/消费数量
    private static final int TOTAL_ITEMS = 10;
    // 生产者/消费者数量
    private static final int PRODUCER_COUNT = 2;
    private static final int CONSUMER_COUNT = 2;

    // 共享缓冲区
    private int[] buffer = new int[BUFFER_SIZE];
    // 生产者放入数据的位置
    private int in = 0;
    // 消费者取出数据的位置
    private int out = 0;

    // 信号量定义
    private Semaphore empty = new Semaphore(BUFFER_SIZE);  // 空缓冲区数量
    private Semaphore full = new Semaphore(0);             // 满缓冲区数量
    private Semaphore mutex = new Semaphore(1);            // 互斥锁（二元信号量）

    // 生产者线程
    class Producer implements Runnable {
        private int id;

        public Producer(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            Random random = new Random();
            // 每个生产者生产的数量 = 总数量 / 生产者数量
            int itemsToProduce = TOTAL_ITEMS / PRODUCER_COUNT;
            for (int i = 0; i < itemsToProduce; i++) {
                try {
                    // 生产一个随机数作为数据
                    int item = random.nextInt(100);
                    // 1. 等待空缓冲区（P操作）
                    empty.acquire();
                    // 2. 获取互斥锁（保证缓冲区操作原子性）
                    mutex.acquire();
                    // 放入数据到缓冲区
                    buffer[in] = item;
                    System.out.printf("生产者 %d 放入数据 %d 到位置 %d，缓冲区: %s%n",
                            id, item, in, arrayToString(buffer));
                    in = (in + 1) % BUFFER_SIZE;
                    // 3. 释放互斥锁（V操作）
                    mutex.release();
                    // 4. 增加满缓冲区计数（通知消费者有新数据）
                    full.release();
                    // 模拟生产耗时
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.printf("生产者 %d 完成生产%n", id);
        }
    }

    // 消费者线程
    class Consumer implements Runnable {
        private int id;
        public Consumer(int id) {
            this.id = id;
        }
        @Override
        public void run() {
            Random random = new Random();
            // 每个消费者消费的数量 = 总数量 / 消费者数量
            int itemsToConsume = TOTAL_ITEMS / CONSUMER_COUNT;

            for (int i = 0; i < itemsToConsume; i++) {
                try {
                    // 1. 等待满缓冲区（P操作）
                    full.acquire();
                    // 2. 获取互斥锁
                    mutex.acquire();
                    // 从缓冲区取出数据
                    int item = buffer[out];
                    System.out.printf("消费者 %d 取出数据 %d 从位置 %d，缓冲区: %s%n",
                            id, item, out, arrayToString(buffer));
                    // 清空已取出数据的位置（可选，仅为了打印直观）
                    buffer[out] = 0;
                    out = (out + 1) % BUFFER_SIZE;
                    // 3. 释放互斥锁
                    mutex.release();
                    // 4. 增加空缓冲区计数（通知生产者有空闲位置）
                    empty.release();
                    // 模拟消费耗时
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.printf("消费者 %d 完成消费%n", id);
        }
    }

    // 辅助方法：将缓冲区数组转为字符串（便于打印）
    private String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        ProducerConsumerDemo pc = new ProducerConsumerDemo();
        // 创建并启动生产者线程
        for (int i = 0; i < PRODUCER_COUNT; i++) {
            Thread producerThread = new Thread(pc.new Producer(i + 1));
            producerThread.start();
        }
        // 创建并启动消费者线程
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            Thread consumerThread = new Thread(pc.new Consumer(i + 1));
            consumerThread.start();
        }
    }
}