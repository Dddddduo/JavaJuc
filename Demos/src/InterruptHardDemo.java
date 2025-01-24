import java.util.concurrent.TimeUnit;

public class InterruptHardDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("t1 线程别打断了程序结束");
                    break;
                }
//                System.out.println("hello isStop");
            }
        }, "t1");
        t1.start();

        System.out.println(t1.isInterrupted());

        // 暂停几秒钟线程
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        new Thread(() ->{
//            // 修改t1线程的中断标志位为true
//            t1.interrupt();
//        },"t2").start();

        t1.interrupt();

        System.out.println(t1.isInterrupted());

    }

}
