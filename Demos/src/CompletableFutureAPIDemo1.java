import java.util.concurrent.*;

public class CompletableFutureAPIDemo1 {
    // 获取结果的方法
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // 创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, // 核心线程数
                4, // 最大线程数
                60, // 线程空闲时间（单位秒）
                TimeUnit.SECONDS, // 时间单位
                new LinkedBlockingQueue<>(10) // 队列容量
        );

        CompletableFuture<Integer> future =
                CompletableFuture.supplyAsync(() -> {
                     try {
                         TimeUnit.SECONDS.sleep(3);
                     } catch (InterruptedException e) {
                        e.printStackTrace();
                     }  return 1;
        });

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 打断
        System.out.println(future.complete(100));
        System.out.println(future.get());


        // 阻塞获取
        System.out.println(future.get());
        // 过时保护 抛出异常
        System.out.println(future.get(2L,TimeUnit.SECONDS));
        // 如果线程池还在计算 那给出9999
        System.out.println(future.getNow(9999));

        // 关闭线程池
        executor.shutdown();
    }

}
