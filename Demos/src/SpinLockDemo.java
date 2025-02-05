import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLockDemo {
    // 演示自旋锁
    AtomicReference<Thread> atomicReference =new AtomicReference<>();
    public void  MyLock(){
        System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
        while (!atomicReference.compareAndSet(null,Thread.currentThread())){
        }
        System.out.println(Thread.currentThread().getName()+"\t"+"---持有锁成功");
    }

    public void  MyUnLock(){
        atomicReference.compareAndSet(Thread.currentThread(),null);
        System.out.println(Thread.currentThread().getName()+"\t"+"---释放锁成功");
    }
    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.MyLock();
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            spinLockDemo.MyUnLock();
        },"t1").start();

        new Thread(() -> {
            spinLockDemo.MyLock();
            try{
                TimeUnit.SECONDS.sleep(5);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            spinLockDemo.MyUnLock();
        },"t2").start();
    }

}
