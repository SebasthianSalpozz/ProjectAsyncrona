public class Message {
    private final String topic;
    private final String content;

    public Message(String topic, String content) {
        this.topic = topic;
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }
}
