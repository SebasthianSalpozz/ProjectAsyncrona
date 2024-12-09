import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    private final String name;

    public Producer(String name) {
        this.name = name;
    }

    public void produce(String topic, String content) {
        Message message = new Message(topic, content);
        System.out.println(name + " is producing: " + content);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(topic, true, false, false, Map.of(
                    "x-dead-letter-exchange", "",
                    "x-dead-letter-routing-key", topic + ".dlq"
            ));

            channel.basicPublish("", topic, null, message.getContent().getBytes());
            System.out.println(" [x] Sent '" + message.getContent() + "'");
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    public static void main(String[] argv) {
        Producer producer = new Producer("Producer1");
        producer.produce("topic", "Hello, RabbitMQ!");
    }
}
