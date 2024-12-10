package Model;

public class DispatchSystem {
    private final Buffer buffer;

    public DispatchSystem(Buffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Добавляет заказ в буфер. Если буфер переполнен, удаляет самый старый заказ.
     *
     * @param order заказ на добавление.
     */
    public void addOrderToBuffer(Order order) {
        if (buffer.isFull()) {
            buffer.rejectOldestOrder(); // Удалить самый старый заказ, чтобы освободить место
        }
        buffer.addOrder(order); // Добавить новый заказ в буфер
    }
}
