import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompleteFutureJoinOrDemo {
    public static void main(String[] args) {
        CompletableFuture.supplyAsync(() -> {
            // 暂停几秒钟的线程
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            };
            return 1;
        }).whenComplete((v,e) ->{
            if(e == null){ // 没有异常
                System.out.println(v);
            }
        }).exceptionally( e ->{
               e.printStackTrace();
               return null;
        }).join();

        System.out.println("---main thread over");
        // 暂停几秒钟主线程
        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        };
    }
}
