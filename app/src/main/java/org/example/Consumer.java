package org.example;

import com.rabbitmq.client.*;

public class Consumer {

    private final String topic;
    
    public Consumer(String topic) {
        this.topic = topic;
    }

    public void consume() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(topic, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(topic, true, deliverCallback, consumerTag -> { });
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    public static void main(String[] argv) throws Exception {
        Consumer consumer = new Consumer("topic");
        consumer.consume();
    }
}
