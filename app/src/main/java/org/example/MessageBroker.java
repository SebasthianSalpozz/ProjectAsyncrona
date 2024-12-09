package org.example;

import java.util.concurrent.*;

public class MessageBroker {
    private final ConcurrentMap<String, BlockingQueue<Message>> topics = new ConcurrentHashMap<>();

    public void createTopic(String topic) {
        topics.putIfAbsent(topic, new LinkedBlockingQueue<>());
    }

    public void publish(String topic, Message message) {
        if (!topics.containsKey(topic)) {
            throw new IllegalArgumentException("Topic not found: " + topic);
        }
        topics.get(topic).offer(message);
    }

    public BlockingQueue<Message> getQueue(String topic) {
        return topics.get(topic);
    }
}