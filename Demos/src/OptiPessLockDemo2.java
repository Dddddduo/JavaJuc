//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.concurrent.locks.ReentrantLock;
//
//public class OptiPessLockDemo2 {
//    // 乐观锁的调用方式
//    private AtomicInteger atomicInteger = new AtomicInteger();
//    atomicInteger.incrementAndGet();
//
//    public static void main(String[] args) {
//        int oldValue = atomicInteger.get();
//        int newValue = oldValue + 1;
//        // 如果值没有被修改，原子性操作成功
//        return atomicInteger.compareAndSet(oldValue, newValue);
//    }
//}
//
//
