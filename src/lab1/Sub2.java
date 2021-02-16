package lab1;

public class Sub2 {

    public synchronized void run() throws InterruptedException {
        // Варіант 18, 2) C=В*МT+D*MX*a;

        System.out.println("Starting sub2...");
        long startTime = System.nanoTime();

        Vector B = new Vector("./sub2/B.txt").filledWithRandomValues();
        Vector D = new Vector("./sub2/D.txt").filledWithRandomValues();

        Matrix MT = new Matrix("./sub2/MT.txt").filledWithRandomValues();
        Matrix MX = new Matrix("./sub2/MX.txt").filledWithRandomValues();

        double fixed_a = 2;

        Vector shared_B = B;
        Vector shared_D = D;

        Thread task1 = new Thread(() -> {
            Matrix copy_MT = MT;
            shared_B.multiplyWithMatrix(copy_MT);
        });

        Thread task2 = new Thread(() -> {
            Matrix copy_MX = MX;
            shared_D.multiplyWithDouble(fixed_a).multiplyWithMatrix(copy_MX);
        });

        Thread task3 = new Thread(() -> {
            System.out.println("C:");
            shared_D.sumWithVector(shared_B).printToConsole().saveToFile("./sub2/C.txt");
            long elapsedTime = System.nanoTime() - startTime;
            System.out.println("Total execution time sub2 in millis: " + elapsedTime / 1000000);
        });

        task1.start();
        task2.start();

        task1.join();
        task2.join();

        task3.start();
        task3.join();

    }

}
