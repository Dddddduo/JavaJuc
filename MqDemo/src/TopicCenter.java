import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主题中心
 * 1.管理订阅关系
 * 2.负责转发消息给订阅者(Subscriber)
 * 所有订阅者都实现了一个接收消息的接口，并且重新该方法，自己实现了逻辑
 */
public class TopicCenter {

    // 这边的核心使用一个 HashMap 来存储主题与订阅者的映射关系
    // 存储主题与订阅者的映射：key=主题名称，value=订阅该主题的所有订阅者
    private final Map<String, List<Subscriber>> topicSubscribers = new HashMap<>();

    /**
     * 订阅主题
     * @param topic 主题名称
     * @param subscriber 订阅者
     */
    public void subscribe(String topic, Subscriber subscriber) {
        // 若主题不存在，初始化该主题的订阅者列表
//        if(topicSubscribers.containsKey(topic)) {
//            topicSubscribers.get(topic).add(subscriber);
//        }else {
//            List<Subscriber> subscribers = new ArrayList<>();
//            subscribers.add(subscriber);
//            topicSubscribers.put(topic, subscribers);
//        }
        topicSubscribers.computeIfAbsent(topic,k -> new ArrayList<>()).add(subscriber);
    }

    /**
     * 取消订阅主题
     * @param topic 主题名称
     * @param subscriber 订阅者
     */
    public void unsubscribe(String topic, Subscriber subscriber) {
        List<Subscriber> subscribers = topicSubscribers.get(topic);
        if (subscribers != null) {
            subscribers.remove(subscriber);
        }
    }

    /**
     * 发布消息到指定主题
     * @param topic 主题名称
     * @param message 消息内容
     */
    public void publish(String topic, String message) {
        List<Subscriber> subscribers = topicSubscribers.get(topic);
        if (subscribers != null) {
            // 遍历所有订阅者，推送消息（这里简化为同步推送，实际可异步）
            for (Subscriber subscriber : subscribers) {
                subscriber.onMessage(message);
            }
        }
    }
}