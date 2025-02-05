import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AtomicMarkableReferenceDemo {
    static AtomicMarkableReference atomicMarkableReference = new AtomicMarkableReference(100,false);
    public static void main(String[] args) {
        new Thread(() ->{
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"---默认修改标识 "+marked);
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            atomicMarkableReference.compareAndSet(100,101,marked,!marked);
        },"t1").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        new Thread(() ->{
            boolean marked = atomicMarkableReference.isMarked();
            System.out.println(Thread.currentThread().getName()+"---默认修改标识 "+marked);
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            boolean b = atomicMarkableReference.compareAndSet(101, 100, marked, !marked);
            System.out.println(Thread.currentThread().getName()+"修改"+b);
            System.out.println(atomicMarkableReference.getReference());
            System.out.println();
        },"t2").start();

    }
}
