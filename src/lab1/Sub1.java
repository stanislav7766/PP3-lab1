package lab1;

public class Sub1 {
    public synchronized void run() throws InterruptedException {
        // Варіант 18, 1) MА= min(D+ В)*MD*MT+MX*ME;

        System.out.println("Starting sub1...");
        long startTime = System.nanoTime();

        Vector D = new Vector("./sub1/D.txt").filledWithRandomValues();
        Vector B = new Vector("./sub1/B.txt").filledWithRandomValues();

        Matrix MD = new Matrix("./sub1/MD.txt").filledWithRandomValues();
        Matrix MT = new Matrix("./sub1/MT.txt").filledWithRandomValues();
        Matrix MX = new Matrix("./sub1/MX.txt").filledWithRandomValues();
        Matrix ME = new Matrix("./sub1/ME.txt").filledWithRandomValues();

        double[] min_D_B = new double[1];

        Matrix shared_MX = MX;
        Matrix shared_MD = MD;

        Thread task1 = new Thread(() -> {
            Vector copy_D = D;
            Vector copy_B = B;
            min_D_B[0] = copy_D.sumWithVector(copy_B).findMin();
        });

        Thread task2 = new Thread(() -> {
            Matrix copy_ME = ME;
            shared_MX.multiplyWithMatrix(copy_ME);
        });

        Thread task3 = new Thread(() -> {
            Matrix copy_MT = MT;
            shared_MD.multiplyWithMatrix(copy_MT);
        });

        Thread task4 = new Thread(() -> {
            System.out.println("MA:");
            shared_MX.multiplyWithMatrix(shared_MD).multiplyWithDouble(min_D_B[0]).printToConsole()
                    .saveToFile("./sub1/MA.txt");
            long elapsedTime = System.nanoTime() - startTime;
            System.out.println("Total execution time sub1 in millis: " + elapsedTime / 1000000);

        });

        task1.start();
        task2.start();
        task3.start();

        task1.join();
        task2.join();
        task3.join();

        task4.start();
        task4.join();

    }

}
