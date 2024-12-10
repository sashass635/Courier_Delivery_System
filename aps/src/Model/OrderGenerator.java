package Model;

import java.util.Random;

public class OrderGenerator {
    private final double lambda;
    private static int orderId = 0;
    private final int id;
    private double nextOrderTime;
    private final Random random;
    public int generatedItemsAmount = 0;

    public OrderGenerator(int id, double lambda) {
        this.id = id;
        this.lambda = lambda;
        this.random = new Random();
    }

    /**
     * @param currentTime Текущее время симуляции.
     * @return Новый объект заказа или null, если время для генерации еще не пришло.
     */
    public Order generateOrder(double currentTime) {
        if (currentTime >= nextOrderTime) { // Проверка, можно ли генерировать заказ
            orderId++;
            generatedItemsAmount++;
            Order newOrder = new Order(orderId, currentTime);
            scheduleNextOrder(); // Запланировать следующий заказ
            return newOrder;
        }
        return null;
    }

    /**
     * @return Время следующего заказа.
     */
    public double getNextOrderTime() {
        return nextOrderTime;
    }

    public int getId() {
        return this.id;
    }

    public void scheduleNextOrder() {
        nextOrderTime += -1.0 * Math.log(Math.random()) / lambda;
    }
}