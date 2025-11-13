/**
 * 邮件订阅者：接收消息后模拟发送邮件
 */
public class EmailSubscriber implements Subscriber {
    private String name; // 订阅者名称

    public EmailSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(String message) {
        System.out.println("【邮件订阅者 " + name + "】收到消息：" + message);
    }
}