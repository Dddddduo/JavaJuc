import java.util.concurrent.atomic.AtomicStampedReference;

public class AvoidABADemo {
    public static void main(String[] args) {
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()  + "\t首次版本号:" + stamp); // 1
            // 暂停一会儿线程
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100,  101, atomicStampedReference.getStamp(),  atomicStampedReference.getStamp()  + 1);
            System.out.println(Thread.currentThread().getName()  + "\t 2次版本号:" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101,  100, atomicStampedReference.getStamp(),  atomicStampedReference.getStamp()  + 1);
            System.out.println(Thread.currentThread().getName()  + "\t 3次版本号:" + atomicStampedReference.getStamp());
        }, "t3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName()  + "\t 首次版本号:" + stamp); // 1
            // 暂停一会儿线程，获得初始值100和初始版本号1，故意暂停3秒钟让t3线程完成一次ABA操作产生问题
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100,  2019, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName()  + "\t" + result + "\t" + atomicStampedReference.getReference());
        }, "t4").start();
    }
}