import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    // 演示ABA

    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {
//        new Thread(() ->{
//            atomicInteger.compareAndSet(100,101);
//            atomicInteger.compareAndSet(101,100);
//        },"t1").start();

        new Thread(()->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("当前版本号:"+stamp);
            atomicStampedReference.compareAndSet(100,101,stamp,stamp+1);
            int stamp2 = atomicStampedReference.getStamp();
            System.out.println("当前版本号:"+stamp2);
            atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            int stamp3 = atomicStampedReference.getStamp();
            System.out.println("当前版本号:"+stamp3);
            },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        new Thread(() ->{
//            boolean b = atomicInteger.compareAndSet(100, 102);
//            System.out.println(Thread.currentThread().getName()+"\t"+"修改成功后与否:"+b+"\t"+atomicInteger.get());
//        },"t2").start();
    }

}
