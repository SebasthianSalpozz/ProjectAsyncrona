public class Producer {
    private final String name;
    private final MessageBroker broker;

    public Producer(String name, MessageBroker broker) {
        this.name = name;
        this.broker = broker;
    }

    public void produce(String topic, String content) {
        Message message = new Message(topic, content);
        System.out.println(name + " is producing: " + content);
        broker.publish(topic, message);
    }
}
