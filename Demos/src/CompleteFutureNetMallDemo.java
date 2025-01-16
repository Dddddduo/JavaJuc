import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompleteFutureNetMallDemo {
    static List<Netmall> list = Arrays.asList(
            new Netmall("jd"),
            new Netmall("tm"),
            new Netmall("pdd")
    );
    // 同步获取
    public static List<String> getPriceByStep(List<Netmall> list ,String productName){
            return list.stream().map(
                    netmall
                            -> String.format(productName+" in %s price is %.2f", netmall.getName(),netmall.calcPrice(productName) ))
                    .collect(Collectors.toList());
    }
    // 异步获取
    public static List<String> getPriceByAsync(List<Netmall> list ,String productName){
        return list.stream().map(
                        netmall
                                -> CompletableFuture.supplyAsync( ()
                                ->
                                String.format(productName+" in %s price is %.2f", netmall.getName(),netmall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }
    public static void main(String[] args) {
        long start1 = System.currentTimeMillis();
        List<String> list1 = getPriceByStep(list, "书");
        for (String s : list1) {
            System.out.println(s);
        }
        long end1 = System.currentTimeMillis();
        System.out.println(String.format("同步方法花费了 %d 毫秒",end1-start1));

        long start2 = System.currentTimeMillis();
        List<String> list2 = getPriceByAsync(list, "书");
        for (String s : list2) {
            System.out.println(s);
        }
        long end2 = System.currentTimeMillis();
        System.out.println(String.format("异步方法花费了 %d 毫秒",end2-start2));
    }
}
class Netmall{
    private String name;
    public Netmall(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    /**
     * 查询价格
     * @param productName
     * @return
     */
    public static double calcPrice(String productName){
        // 检索需要1秒钟
        try{
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return ThreadLocalRandom.current().nextDouble()*2+productName.charAt(0);
    }
}
