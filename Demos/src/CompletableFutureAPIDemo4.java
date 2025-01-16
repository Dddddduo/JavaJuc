import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureAPIDemo4 {
    // applyToEither 斗地主 比赛 看谁能拿到数据
    public static void main(String[] args) {
        while (true){
            CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1;
            }).applyToEither(CompletableFuture.supplyAsync(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 2;
            }), r -> {
                System.out.println(r);
                return r;
            }).join();
        }
    }
}
