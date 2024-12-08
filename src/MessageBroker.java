import java.util.concurrent.*;
import java.util.*;

public class MessageBroker {
    private final Map<String, BlockingQueue<Message>> topics = new ConcurrentHashMap<>();
    private final Map<String, List<Consumer>> consumers = new ConcurrentHashMap<>();

    public void createTopic(String topic) {
        topics.putIfAbsent(topic, new LinkedBlockingQueue<>());
        consumers.putIfAbsent(topic, new ArrayList<>());
    }

    public void publish(String topic, Message message) {
        if (!topics.containsKey(topic)) {
            throw new IllegalArgumentException("Topic not found: " + topic);
        }
        topics.get(topic).offer(message);
        notifyConsumers(topic);
    }

    public void subscribe(String topic, Consumer consumer) {
        if (!consumers.containsKey(topic)) {
            throw new IllegalArgumentException("Topic not found: " + topic);
        }
        consumers.get(topic).add(consumer);
    }

    private void notifyConsumers(String topic) {
        List<Consumer> topicConsumers = consumers.get(topic);
        if (topicConsumers != null) {
            for (Consumer consumer : topicConsumers) {
                CompletableFuture.runAsync(() -> {
                    try {
                        Message message = topics.get(topic).poll(1, TimeUnit.SECONDS);
                        if (message != null) {
                            consumer.consume(message);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.err.println("Consumer interrupted: " + e.getMessage());
                    }
                });
            }
        }
    }
}
