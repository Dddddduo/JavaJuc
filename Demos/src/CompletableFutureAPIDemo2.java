import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAPIDemo2 {
    // 对结果进行处理
    public static void main(String[] args) {
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, // 核心线程数
                4, // 最大线程数
                60, // 线程空闲时间（单位秒）
                TimeUnit.SECONDS, // 时间单位
                new LinkedBlockingQueue<>(10) // 队列容量
        );

        CompletableFuture.supplyAsync(()->{
            System.out.println("第一步");
            return 1;
        }).thenApply(f -> {
            System.out.println("第二步");
            return f+2;
        }).thenApply( f ->{
            System.out.println("第三步");
            return f+3;
        }).whenComplete( (v,e) ->{
            if(e==null){
                System.out.println("result: " + v);
            }
        }).exceptionally(e ->{
            e.printStackTrace();
            return null;
        }).join();

        CompletableFuture.supplyAsync(()->{
            System.out.println("第一步");
            return 1;
        }).handle((f,e) -> {
            System.out.println("第二步");
            return f+2;
        }).handle( (f,e) ->{
            System.out.println("第三步");
            return f+3;
        }).whenComplete( (v,e) ->{
            if(e==null){
                System.out.println("result: " + v);
            }
        }).exceptionally(e ->{
            e.printStackTrace();
            return null;
        }).join();

        // 关闭线程池
        executor.shutdown();
    }

}
