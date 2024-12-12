package Model;

import java.util.Random;

public class OrderGenerator {
    private final double lambda;
    private static int orderId = 0;
    private final int id;
    private double nextOrderTime;
    private final Random random;
    public int generatedItemsAmount = 0;
    private double totalWaitTime = 0.0; // Общее время ожидания
    private int rejectedOrders = 0; // Количество отказов

    public OrderGenerator(int id, double lambda) {
        this.id = id;
        this.lambda = lambda;
        this.random = new Random();
    }

    public Order generateOrder(double currentTime) {
        if (currentTime >= nextOrderTime) {
            orderId++;
            generatedItemsAmount++;
            Order newOrder = new Order(orderId, currentTime);
            scheduleNextOrder();
            return newOrder;
        } else {
            rejectedOrders++;
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

    public double getAverageWaitTime() {
        return generatedItemsAmount > 0 ? totalWaitTime / generatedItemsAmount : 0.0;
    }

    public int getRejectedOrders() {
        return rejectedOrders;
    }
}