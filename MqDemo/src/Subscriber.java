/**
 * 订阅者接口：定义接收消息的方法
 */
public interface Subscriber {
    // 接收消息的方法，参数为消息内容
    void onMessage(String message);
}