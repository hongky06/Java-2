package Session14.FlashSale.com.flashsale.test;



import Session14.FlashSale.com.flashsale.service.OrderService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrencyTest {
    public static void runTest() {
        System.out.println("Starting Concurrency Test...");
        
        int numberOfThreads = 50;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        OrderService orderService = new OrderService();
        
        for (int i = 0; i < numberOfThreads; i++) {
            int userId = 1;
            executor.submit(() -> {
                orderService.placeOrder(userId, 1, 1);
            });
        }
        
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("Concurrency Test Finished.");
    }
}
