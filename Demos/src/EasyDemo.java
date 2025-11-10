import java.util.concurrent.CountDownLatch;

public class EasyDemo {

    static int num = 0;

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(20);

        Thread[] threads = new Thread[20];

        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 20; i1++) {
                    num++;
                }
            });
            threads[i].start();
            countDownLatch.countDown();
        }

//        for(int i=0;i<20;i++){
//            try {
//                threads[i].join();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(num);

    }
}
