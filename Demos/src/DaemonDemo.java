import java.util.concurrent.TimeUnit;

public class DaemonDemo {
    // 演示守护线程和用户线程
    public static void main(String[] args) {

        // 举例 main主线程+后台GC守护线程

        // 创建一个线程a
        Thread a=new Thread(() ->{
            // 判断a线程是什么线程
            System.out.println(Thread.currentThread().getName()
                    +" come in : \t"+ ((Thread.currentThread().isDaemon())
                    ? "守护线程" : "用户线程"));
            // 让a线程一直执行
            while (true){}
        },"a");

        // 设置a为守护线程
        a.setDaemon(true);
        // 启动线程a
        a.start();

        // 暂停几秒主线程
        try {
            TimeUnit.SECONDS.sleep(2);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        // 主线程执行到这里 是方法的最后一个语句 自然会结束
        System.out.println(Thread.currentThread().getName()+"\t"+"over");
    }
}
