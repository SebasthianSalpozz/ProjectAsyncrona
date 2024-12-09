package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.queueDeclare(topic, false, false, false, null);
            channel.basicPublish("",  message.getTopic(), null, message.getContent().getBytes());
            System.out.println(" [x] Sent '" +  message.getContent() + "'");
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    public static void main(String[] argv) throws Exception {
        Producer producer = new Producer("topic", new MessageBroker());
        producer.produce("topic", "send email");
    }
}
