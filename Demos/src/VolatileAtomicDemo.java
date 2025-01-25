import java.util.concurrent.TimeUnit;

class MyNumber{
    volatile int number = 0;
    public void  addPlusPlus(){
        number++;
    }
}

public class VolatileAtomicDemo {
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();
        for (int i = 1; i <= 10; i++) {
            new Thread(() ->{
                for (int i1 = 1; i1 <= 1000; i1++) {
                    myNumber.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }

        try{
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+"\t"+myNumber.number);

    }
}
