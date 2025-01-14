public class MonitorDemo {
    // 什么是管程
    public static void main(String[] args) {

        Object o = new Object();

        new Thread(() ->{

            // 监视器
            synchronized (o){

            }

        }, "t1").start();

    }
}
