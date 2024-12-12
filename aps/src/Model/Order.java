package Model;

public class Order {
    private final int id;
    private final double orderTime;
    private double dispatchTime;
    private double deliveryTime; // Время выполнения доставки
    private boolean rejected;
    private int courierId;

    /**
     * @param id Уникальный идентификатор заказа.
     * @param orderTime Время создания заказа.
     */
    public Order(int id, double orderTime) {
        this.id = id;
        this.orderTime = orderTime;
        this.rejected = false;
    }

    public int getId() {
        return id;
    }

    public double getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(double dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public double getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(double deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    public void rejectOrder() {
        this.rejected = true;
    }
}
