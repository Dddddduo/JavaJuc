/**
 * 订阅者接口：定义接收消息的方法
 */
public interface Subscriber {

    // 推送消息
    void onMessage(String message);

}