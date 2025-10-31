import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {

    public static final int SIZE_=50;
    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE_);
        for(int i=1;i<=SIZE_;i++){
            new Thread(()->{
                try {
                    for (int i1 = 0; i1 < 100; i1++) {
                        myNumber.addPlusPlus();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    // 每个线程执行完后 都要 countDown 一次
                    countDownLatch.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        // 因为 main 线程太快了 会提前进入
        System.out.println(myNumber.number);
    }
}
// 资源类
class MyAtomicNumber{
    AtomicInteger atomicInteger=new AtomicInteger();
    public void addPlusPlus(){
        atomicInteger.incrementAndGet();
    }
}
