import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskDemo {

    // 练习Future 和 Callable 接口
    public static void main(String[] args) throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<>(() -> {
            // 打印当前线程名
            System.out.println(Thread.currentThread().getName()
                    +"\t"
                    +"--- come in");
            // 线程休眠
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e){e.printStackTrace();}
            return 1024;
        });
        new Thread(futureTask,"t1") .start();

        // 阻塞
//        System.out.println(futureTask.get());
        // 只等两秒钟
//        System.out.println(futureTask.get(2L,TimeUnit.SECONDS));

        // 轮询
        while (true){
            if(futureTask.isDone()){
                System.out.println(futureTask.get());
                break;
            }else System.out.println("还在计算中");
        }

    }
}
