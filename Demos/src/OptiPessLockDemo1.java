import java.util.concurrent.locks.ReentrantLock;

public class OptiPessLockDemo1 {
    // 悲观锁的调用方式
    public  synchronized void m1(){
        // 加锁后的业务逻辑...
    }

    // 保证多个线程使用的是同一个lock对象的前提下
    ReentrantLock lock=new ReentrantLock();

    public void m2(){
        lock.lock();
        try {
            // 操作同步资源
        }finally {
            lock.unlock();
        }
    }

    // 两个都是悲观锁
}
