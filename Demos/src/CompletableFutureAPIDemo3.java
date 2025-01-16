import java.util.concurrent.CompletableFuture;

public class CompletableFutureAPIDemo3 {
    // 对结果进行消费
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(()->{
            return 1;
        }).thenApply(f -> {
            return f+1;
        }).thenApply(f -> {
            return f+3;
        }).thenAccept(r -> System.out.println(r));

        // thenRun 无输入值 无返回值
        CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {}).join();
        // thenAccept 有输入值 无返回值
        CompletableFuture.supplyAsync(() -> "resultA").thenAccept(resultA -> {}).join();
        // thenApply 有输入值 有返回值
        CompletableFuture.supplyAsync(() -> "resultA").thenApply(resultA -> resultA).join();

    }
}
