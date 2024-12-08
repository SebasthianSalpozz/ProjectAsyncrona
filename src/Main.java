public class Main {
    public static void main(String[] args) {
        MessageBroker broker = new MessageBroker();

        broker.createTopic("news");
        broker.createTopic("sports");

        Consumer consumer1 = new Consumer("Consumer1");
        Consumer consumer2 = new Consumer("Consumer2");

        broker.subscribe("news", consumer1);
        broker.subscribe("sports", consumer2);

        Producer producer1 = new Producer("Producer1", broker);
        Producer producer2 = new Producer("Producer2", broker);

        producer1.produce("news", "Breaking news: Java is awesome!");
        producer2.produce("sports", "Sports update: Java beats Python in a race!");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
