// 这边是用重锁写的一个示例
// 其中interrupt方法是中断当前线程,防止死锁
// wait()方法是阻塞 必须在synchronized同步代码块里执行
// notify()方法是唤醒 必须在synchronized同步代码块里面执行
public class SyncDemo {

    private static final Object lock = new Object();
    private static boolean flag = false;
    public static void main(String[] args) {

        Thread t1 = new Thread(()->{
            for (int i = 1; i <= 10; i+=2) {
                synchronized (lock) {
                    while (flag==true) {
                        try{
                            lock.wait();
                        }catch (InterruptedException e){
                            Thread.currentThread().interrupt();
                        }
                    }
                    flag=true;
                    System.out.println(Thread.currentThread().getName()+" "+i);
                    lock.notify();
                }
            }
        },"线程1");

        Thread t2 = new Thread(()->{
            for (int i = 2; i <= 10; i+=2) {
                synchronized (lock){
                    while(flag==false){
                        try{
                            lock.wait();
                        }catch (InterruptedException e){
                            Thread.currentThread().interrupt();
                        }
                    }
                    flag=false;
                    System.out.println(Thread.currentThread().getName()+" "+i);
                    lock.notify();
                }
            }
        },"线程2");

        t1.start();
        t2.start();
    }

}
