import java.util.concurrent.*;

public class CompletableFutureDemo {
    // 练习一下 CompleteableFuture
    public static void main(String[] args) {
        // 异步一个线程去干活
        CompletableFuture<Void> CompletableFuture1 =
                CompletableFuture.runAsync(() -> {
                    System.out.println("---come in");
                });

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

        // 创建一个CompletableFuture对象 带线程池的
        CompletableFuture<Void> CompletableFuture2 =
                CompletableFuture.runAsync(() -> {
                    System.out.println("---come in");
                },executor);

        // 关掉线程池
        executor.shutdown();

        // 有返回的异步方法
        CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName());
            return 1;
        });

        CompletableFuture.supplyAsync(()->{
            System.out.println(Thread.currentThread().getName());
            return 1;
        },executor);

    }
}
