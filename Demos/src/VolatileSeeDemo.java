import java.util.concurrent.TimeUnit;

public class VolatileSeeDemo {

//    static boolean flag = true;
    static volatile boolean flag = true;
    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
            while (flag){
                new Integer(308);
            }
            System.out.println("t1 over");
        },"t1").start();

        try{
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        new Thread(() ->{
            flag = false ;
        },"t2").start();

    }

}
