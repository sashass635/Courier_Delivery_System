package Model;

import java.util.LinkedList;

public class Buffer {
    private final int capacity; // Максимальное количество заказов в буфере
    private final LinkedList<Order> orders; 

    public Buffer(int capacity) {
        this.capacity = capacity;
        this.orders = new LinkedList<>();
    }

    public boolean isFull() {
        return orders.size() >= capacity;
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public void addOrder(Order order) {
        orders.addFirst(order); // Добавление в начало очереди (LIFO)
    }

    public Order getNextOrder() {
        return !isEmpty() ? orders.removeFirst() : null;
    }

    public void rejectOldestOrder() {
        if (!isEmpty()) {
            Order oldestOrder = orders.removeLast(); // Удалить самый старый заказ
            oldestOrder.rejectOrder();
            System.out.println("Заказ " + oldestOrder.getId() + " отклонен (переполнение буфера).");
        }
    }
    public void info() { System.out.println("Buffer size: " + orders.size() + "/" + capacity);
        System.out.println("+--------------+--------+"); System.out.println("| Buffer Index | Status |");
        System.out.println("+--------------+--------+"); for (int i = 0; i < capacity; i++) {
            if (i < orders.size()) {
                System.out.printf("| %-12d | %-6s |\n", i, "Call " + orders.get(i).getId()); }
            else { System.out.printf("| %-12d | %-6s |\n", i, "empty"); } }
        System.out.println("+--------------+--------+"); }
}
