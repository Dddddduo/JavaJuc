import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReEntryLockDemo {
    // 演示递归锁
    static Object objectLock=new Object();
    // 支持递归
    public synchronized void  m1(){
        m1();
    }
    public static void main(String[] args) {
//        new Thread(() -> {
//            synchronized (objectLock){
//                System.out.println("----外层");
//                synchronized (objectLock){
//                    System.out.println("----中层");
//                    synchronized (objectLock){
//                        System.out.println("----内层");
//                    }
//                }
//            }
//        },"t1").start();
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() ->{
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"\t"+"---外层");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName()+"\t"+"---内层");
                }finally {
//                    lock.unlock();
                }
            }finally {
                lock.unlock();
            }
        },"t1").start();

        new Thread(() ->{
            lock.lock();
            try{
                System.out.println(Thread.currentThread().getName()+"\t"+"---第二把锁");
            }finally {
                lock.unlock();
            }
        },"t2").start();
    }
}
