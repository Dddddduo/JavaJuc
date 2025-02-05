import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDemo {
    public static void main(String[] args) {
        // 定义原子整型类型的数据
        AtomicInteger atomicInteger1 = new AtomicInteger(1);
        AtomicInteger atomicInteger2 = new AtomicInteger(2);
        AtomicInteger atomicInteger3 = new AtomicInteger(3);

        // 原子整型的数据 数组
//        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1, 2, 3});

        // 创建一个长度为5的AtomicIntegerArray，初始值都为0
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[5]);

        // 打印数组初始值
        for (int i = 0; i < atomicIntegerArray.length();  i++) {
            System.out.println(atomicIntegerArray.get(i));
        }

        int tmpInt = 0;
        // 将数组中索引为0的元素设置为1122，并返回原来的值
        tmpInt = atomicIntegerArray.getAndSet(0,  1122);
        System.out.println(tmpInt  + "\t" + atomicIntegerArray.get(0));

        // 对数组中索引为1的元素进行自增操作
        atomicIntegerArray.getAndIncrement(1);
        atomicIntegerArray.getAndIncrement(1);
        // 对数组中索引为1的元素进行自增操作，并返回原来的值
        tmpInt = atomicIntegerArray.getAndIncrement(1);
        System.out.println(tmpInt  + "\t" + atomicIntegerArray.get(1));
    }

}
