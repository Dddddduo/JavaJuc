/**
 * 短信订阅者：接收消息后模拟发送短信
 */
public class SmsSubscriber implements Subscriber {
    private String phone; // 订阅者手机号

    public SmsSubscriber(String phone) {
        this.phone = phone;
    }

    @Override
    public void onMessage(String message) {
        System.out.println("【短信订阅者 " + phone + "】收到消息：" + message);
    }
}
