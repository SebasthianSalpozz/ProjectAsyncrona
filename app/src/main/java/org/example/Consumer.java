package org.example;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.example.exceptions.SendMessageException;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Consumer {

    private final String topic;

    public Consumer(String topic) {
        this.topic = topic;
    }

    public void consume() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            String dlqName = topic + ".dlq";
            channel.queueDeclare(dlqName, true, false, false, null);

            channel.queueDeclare(topic, true, false, false, Map.of(
                    "x-dead-letter-exchange", "",
                    "x-dead-letter-routing-key", dlqName));

            channel.basicQos(1);

            System.out.println(" [*] Waiting for messages on topic: " + topic);

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                try {
                    System.out.println(" [x] Processing message: " + message);

                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                } catch (Exception e) {
                    System.err.println(" [!] Error processing message: " + message);
                    channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
                }
            };
            channel.basicConsume(topic, true, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            throw new SendMessageException("Connection error with the topic", e);
        } catch (TimeoutException e) {
            throw new SendMessageException("time exceeded to send the message", e);
        }
    }

    public static void main(String[] argv) {
        Consumer consumer = new Consumer("topic");
        consumer.consume();
    }
}