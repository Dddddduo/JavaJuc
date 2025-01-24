public class WaitAndNotifyDemo {

    static Object obj=new Object();
    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (obj){
                System.out.println(Thread.currentThread().getName()+"\t"+"---come in");
                try {
                    obj.wait();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t"+"---被唤醒");
            }
        },"t1").start();

        new Thread(() ->{
            synchronized (obj){
                obj.notify();
                System.out.println(Thread.currentThread().getName()+"\t"+"---发出通知");
            }
        },"t2").start();

    }
}
