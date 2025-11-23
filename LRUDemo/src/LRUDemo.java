import java.util.HashMap;
import java.util.Map;

public class LRUDemo {
    private final Map<Integer, Node> cache; // key -> 节点
    private final Node head; // 虚拟头节点（最近使用）
    private final Node tail; // 虚拟尾节点（最久未使用）
    private final int capacity; // 缓存容量

    class Node {
        public int key;
        public int value;
        public Node prev; // 前驱节点
        public Node next; // 后继节点

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUDemo(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>(capacity);
        // 初始化虚拟头尾节点，避免边界判断
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    // 获取缓存值
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1; // 未找到返回 -1
        }
        Node node = cache.get(key);
        moveToHead(node); // 访问后移到头部（最近使用）
        return node.value;
    }

    // 存入缓存
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            // 已存在：更新值 + 移到头部
            Node node = cache.get(key);
            node.value = value;
            moveToHead(node);
            return;
        }
        // 不存在：创建新节点
        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        addToHead(newNode); // 插入头部
        // 检查容量：超出则删除尾节点（最久未使用）
        if (cache.size() > capacity) {
            Node tailNode = removeTail();
            cache.remove(tailNode.key);
        }
    }

    // 辅助方法：将节点移到头部
    private void moveToHead(Node node) {
        removeNode(node); // 先删除当前位置
        addToHead(node);  // 再插入头部
    }

    // 辅助方法：删除节点
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // 辅助方法：在头部插入节点
    private void addToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    // 辅助方法：删除尾节点（最久未使用）
    private Node removeTail() {
        Node tailNode = tail.prev;
        removeNode(tailNode);
        return tailNode;
    }

    // 测试用：打印缓存内容（从最近到最久）
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node curr = head.next;
        while (curr != tail) {
            sb.append(curr.key).append("=").append(curr.value).append(", ");
            curr = curr.next;
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
    }

    // 测试
    public static void main(String[] args) {
        LRUDemo lru = new LRUDemo(2);
        lru.put(1, 1);
        System.out.println("put(1,1) -> " + lru); // 1=1

        lru.put(2, 2);
        System.out.println("put(2,2) -> " + lru); // 2=2, 1=1

        System.out.println("get(1) -> " + lru.get(1)); // 1
        System.out.println("after get(1) -> " + lru); // 1=1, 2=2

        lru.put(3, 3); // 淘汰 2
        System.out.println("put(3,3) -> " + lru); // 3=3, 1=1

        System.out.println("get(2) -> " + lru.get(2)); // -1

        lru.put(4, 4); // 淘汰 1
        System.out.println("put(4,4) -> " + lru); // 4=4, 3=3

        System.out.println("get(1) -> " + lru.get(1)); // -1
        System.out.println("get(3) -> " + lru.get(3)); // 3
        System.out.println("after get(3) -> " + lru); // 3=3, 4=4
    }
}