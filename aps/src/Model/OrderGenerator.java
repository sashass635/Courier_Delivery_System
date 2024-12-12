package Model;

import java.util.Random;

public class OrderGenerator {
    private final double lambda;
    private static int orderId = 0;
    private final int id;
    private double nextOrderTime;
    private final Random random;
    public int generatedItemsAmount = 0;
    private int totalRequests = 0;
    private int rejectedOrders = 0;
    private double totalWaitTime = 0.0;
    private double totalProcessTime = 0.0;
    private double totalWaitTimeSquared = 0.0;
    private double totalProcessTimeSquared = 0.0;

    public OrderGenerator(int id, double lambda) {
        this.id = id;
        this.lambda = lambda;
        this.random = new Random();
    }

    public Order generateOrder(double currentTime) {
        totalRequests++;
        if (currentTime >= nextOrderTime) {
            orderId++;
            generatedItemsAmount++; 
            Order newOrder = new Order(orderId, currentTime);

            double waitTime = currentTime - nextOrderTime; // Время ожидания
            totalWaitTime += waitTime;
            totalWaitTimeSquared += waitTime * waitTime; 

            double processTime = random.nextDouble() * 10; // случайное время обработки от 0 до 10
            totalProcessTime += processTime;
            totalProcessTimeSquared += processTime * processTime; 

            scheduleNextOrder(); // Запланировать следующий заказ
            return newOrder;
        } else {
            if (random.nextDouble() < 0.1) { // 10% вероятность отказа
                rejectedOrders++; 
                return null;
            }
            return null;
        }
    }
    public double getNextOrderTime() {
        return nextOrderTime;
    }

    public void incrementRejectedOrders() {
        rejectedOrders++;
    }

    public int getId() {
        return this.id;
    }

    public void scheduleNextOrder() {
        nextOrderTime += -1.0 * Math.log(Math.random()) / lambda;
    }

    public int getRejectedOrders() {
        return rejectedOrders;
    }

    public int getTotalRequests() {
        return totalRequests;
    }

    public double getAverageWaitTime() {
        return generatedItemsAmount == 0 ? 0 : totalWaitTime / generatedItemsAmount;
    }

    public double getAverageProcessTime() {
        return generatedItemsAmount > 0 ? totalProcessTime / generatedItemsAmount : 0.0;
    }

    public double getWaitTimeVariance() {
        if (generatedItemsAmount > 0) {
            double mean = getAverageWaitTime();
            return (totalWaitTimeSquared / generatedItemsAmount) - (mean * mean);
        }
        return 0.0;
    }

    public double getProcessTimeVariance() {
        if (generatedItemsAmount > 0) {
            double mean = getAverageProcessTime();
            return (totalProcessTimeSquared / generatedItemsAmount) - (mean * mean);
        }
        return 0.0;
    }

    public int getGeneratedOrders() {
        return generatedItemsAmount;
    }
}
