import java.util.concurrent.TimeUnit;

// 资源类
class phone{
    // 演示 普通方法 同步方法 静态同步方法
    // 类锁 对象锁
    public static synchronized void sendEmail(){
        // 暂停几秒钟的线程
        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---email---");
    }
    public synchronized void sendSMS(){
        System.out.println("---sms---");
    }
    public void hello(){
        System.out.println("---hello---");
    }

}
public class LockDemo {
    public static void main(String[] args) { // 主线程
        phone phone = new phone(); // 资源类
        phone phone2 = new phone(); // 资源类

        new Thread(()->{
            phone.sendEmail();
        },"a").start();

        // 暂停几秒钟的线程
        try{
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            phone.sendSMS();
        },"b").start();

    }
}
