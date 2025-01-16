import java.util.concurrent.CompletableFuture;

public class CompletableFutureAPIDemo5 {
    // thenCombine 一个线程拿到数据后 会等待另一个数据获取结束
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(()->{
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(()->{
            return 20;
        }),(r1,r2) -> {
            return r1 + r2;
        }).join();
    }
}
