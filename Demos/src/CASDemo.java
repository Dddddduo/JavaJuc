import java.util.concurrent.atomic.AtomicInteger;

public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);

        System.out.println(atomicInteger.compareAndSet(5,100));
        System.out.println(atomicInteger.get());

        System.out.println(atomicInteger.compareAndSet(1,100));
        System.out.println(atomicInteger.get());
    }
}
