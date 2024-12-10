package Model;

import java.util.List;

public class CourierManager {
    public final List<Courier> couriers;

    public CourierManager(List<Courier> couriers) {
        this.couriers = couriers;
    }

    /**
     * Находим первого доступного курьера.
     *
     * @return доступный курьер или null, если все заняты.
     */
    private Courier findAvailableCourier() {
        return couriers.stream().filter(courier -> !courier.isBusy()).findFirst().orElse(null);
    }

    /**
     * Выбираем заказ из буфера (последний добавленный, LIFO) и назначает его курьеру.
     */
    public void assignOrderToCourier(Buffer buffer, double currentTime) {
        if (buffer.isEmpty()) {
            return;
        }
        Courier availableCourier = findAvailableCourier();
        if (availableCourier == null) {
            return;
        }
        Order order = buffer.getNextOrder(); // Получить заказ из буфера
        availableCourier.assignOrder(order, currentTime); // Назначить заказ курьеру
    }

}
