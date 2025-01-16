import java.sql.Time;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.*;

public class CompletableFutureHardDemo {
    // 比较难的东西
    public static void mainOld(String[] args) throws Exception{

        Deque<Integer> deque = new LinkedList<>();

        // 创建一个线程池，最大线程数为5
        int corePoolSize = 2; // 核心线程数
        int maximumPoolSize = 5; // 最大线程数
        long keepAliveTime = 60; // 空闲线程存活时间
        TimeUnit unit = TimeUnit.SECONDS; // 时间单位

        // 创建线程池
        ExecutorService executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                unit,
                new LinkedBlockingQueue<Runnable>()
        );

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t" + "来啦");
            // 暂停几秒种线程
            try {
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return 1;
        },executor);
        System.out.println(future.get());
        System.out.println("main over");
        // 关掉线程池
        executor.shutdown();
    }

    public static void main(String[] args) {
        // 高级写法 异步编排 多线程异步调用
        CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            return 1;
        }).thenApply( f -> {
            return f+2;
        }).whenComplete( (v,e) ->{
            if(e==null){
                System.out.println("result:"+v);
            }
        }).exceptionally(e ->{
            e.printStackTrace();
            return null;
        });

        System.out.println("--- main over");
        // 主线程不能立马结束 否则默认线程池也会结束
        // 暂停几秒种线程
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
