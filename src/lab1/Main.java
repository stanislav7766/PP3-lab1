package lab1;

public class Main {
    public synchronized static void main(String[] args) throws InterruptedException {
        new Sub1().run();
        new Sub2().run();
    }
}
