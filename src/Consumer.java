public class Consumer {
    private final String name;

    public Consumer(String name) {
        this.name = name;
    }

    public void consume(Message message) {
        System.out.println(name + " received: " + message.getContent());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Processing interrupted: " + e.getMessage());
        }
    }
}
