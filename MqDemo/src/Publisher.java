/**
 * 发布者：向主题中心发布消息
 */
class Publisher{

    private TopicCenter topicCenter; // 依赖主题中心

    public Publisher(TopicCenter topicCenter) {
        this.topicCenter = topicCenter;
    }

    /**
     * 发布新闻到指定主题
     */
    public void publishNews(String topic, String news) {
        System.out.println("\n=== 发布新闻到【" + topic + "】：" + news);
        topicCenter.publish(topic, news);
    }

}