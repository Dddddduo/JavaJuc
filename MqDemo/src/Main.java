public class Main {

    /**
     * 消息队列的发布订阅模型
     * 1.创建消息订阅者,最简单的模型
     * 2.创建主题中心,主题中心管理了主题跟订阅者的关系，将主题跟订阅者进行绑定(Map<String,List<>())
     * 3.消息发布者,依赖于主题中心,创建后能发送不同主题的消息
     * 4.消息发布者调用内部依赖的主题中心的方法,发布消息给不同的订阅者
     * @param args
     */
    public static void main(String[] args) {
        // 1. 创建主题中心
        TopicCenter topicCenter = new TopicCenter();

        // 2. 创建订阅者
        Subscriber emailSub1 = new EmailSubscriber("alice@example.com");
        Subscriber emailSub2 = new EmailSubscriber("bob@example.com");
        Subscriber smsSub1 = new SmsSubscriber("13800138000");

        // 3. 订阅主题（例如："科技新闻"和"体育新闻"）
        topicCenter.subscribe("科技新闻", emailSub1);
        topicCenter.subscribe("科技新闻", smsSub1);
        topicCenter.subscribe("体育新闻", emailSub2);
        topicCenter.subscribe("体育新闻", smsSub1);

        // 4. 创建发布者并发布消息
        Publisher publisher = new Publisher(topicCenter);
        publisher.publishNews("科技新闻", "Java 21 正式发布！");
        publisher.publishNews("体育新闻", "国足晋级世界杯！");

        // 5. 测试取消订阅（例如：smsSub1 取消订阅"体育新闻"）
        topicCenter.unsubscribe("体育新闻", smsSub1);
        publisher.publishNews("体育新闻", "奥运会延期举办");
    }

}