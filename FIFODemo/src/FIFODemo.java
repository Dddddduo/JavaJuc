import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 使用 HashMap 和 Queue 实现的 FIFO 缓存
 */
public class FIFODemo {

    private final Map<Integer, Integer> cache;
    private final Queue<Integer> queue;
    private final int capacity;

    /**
     * 构造函数
     * @param capacity 缓存的最大容量
     */
    public FIFODemo(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.queue = new LinkedList<>();
    }

    /**
     * 从缓存中获取一个值
     * @param key 要获取的键
     * @return 如果 key 存在，返回对应的值；否则返回 -1
     */
    public int get(int key) {
        // HashMap 的 get 方法是 O(1)
        return cache.getOrDefault(key, -1);
    }

    /**
     * 向缓存中放入一个键值对
     * @param key 键
     * @param value 值
     */
    public void put(int key, int value) {
        // 1. 如果 key 已经存在，更新其值即可，不需要改变队列顺序
        if (cache.containsKey(key)) {
            cache.put(key, value);
            return;
        }

        // 2. 如果 key 不存在，需要插入新值
        // 2.1. 检查缓存是否已满
        if (cache.size() >= capacity) {
            // 缓存已满，淘汰队列头部的元素（最早进入的元素）
            int oldestKey = queue.poll(); // Queue 的 poll 方法是 O(1)
            cache.remove(oldestKey);     // HashMap 的 remove 方法是 O(1)
            System.out.println("Cache is full. Evicting key: " + oldestKey);
        }

        // 2.2. 将新的 key-value 对放入缓存和队列
        cache.put(key, value);          // HashMap 的 put 方法是 O(1)
        queue.offer(key);               // Queue 的 offer 方法是 O(1)
    }

    /**
     * 用于调试，打印当前缓存中的所有条目
     */
    @Override
    public String toString() {
        return cache.toString();
    }

    // 测试
    public static void main(String[] args) {
        FIFODemo fifoCache = new FIFODemo(2);

        fifoCache.put(1, 1);
        System.out.println("After put(1,1): " + fifoCache); // 输出: {1=1}

        fifoCache.put(2, 2);
        System.out.println("After put(2,2): " + fifoCache); // 输出: {1=1, 2=2}

        System.out.println("get(1) returns: " + fifoCache.get(1)); // 输出: 1
        System.out.println("After get(1): " + fifoCache); // 输出: {1=1, 2=2} (顺序未变)

        // 缓存已满，放入新元素 3，淘汰最老的元素 1
        fifoCache.put(3, 3);
        System.out.println("After put(3,3): " + fifoCache); // 输出: {2=2, 3=3}

        System.out.println("get(1) returns: " + fifoCache.get(1)); // 输出: -1 (1 已被淘汰)

        // 缓存已满，放入新元素 4，淘汰最老的元素 2
        fifoCache.put(4, 4);
        System.out.println("After put(4,4): " + fifoCache); // 输出: {3=3, 4=4}

        System.out.println("get(2) returns: " + fifoCache.get(2)); // 输出: -1 (2 已被淘汰)
        System.out.println("get(3) returns: " + fifoCache.get(3)); // 输出: 3
        System.out.println("get(4) returns: " + fifoCache.get(4)); // 输出: 4
    }
}