package Model;

public class Courier {
    private final int id;
    private boolean isBusy;
    private Order currentOrder = null;
    private double totalWorkTime = 0.0;
    private int orderAmount = 0;

    public Courier(int id) {
        this.id = id;
        this.isBusy = false;
    }

    public double generateServiceTime() {
        double minServiceTime = 1.0;
        double maxServiceTime = 5.0;
        return minServiceTime + (maxServiceTime - minServiceTime) * Math.random();
    }

    public int getId() {
        return id;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public boolean hasCompletedOrder(double currentTime) {
        if (currentOrder != null) {
            double completionTime = currentOrder.getDispatchTime() + currentOrder.getDeliveryTime();
            return currentTime >= completionTime;
        }
        return false;
    }

    public void assignOrder(Order order, double currentTime) {
        orderAmount++;
        this.isBusy = true;
        this.currentOrder = order;
        order.setDispatchTime(currentTime);
        order.setCourierId(this.id);
        double serviceTime = generateServiceTime();
        order.setDeliveryTime(serviceTime);
    }

    public Order releaseOrder() {
        if (currentOrder != null) {
            double completionTime = currentOrder.getDispatchTime() + currentOrder.getDeliveryTime();
            double workTime = completionTime - currentOrder.getDispatchTime();

            totalWorkTime += workTime;
            Order completedOrder = currentOrder;
            currentOrder = null;
            isBusy = false;
            return completedOrder;
        }
        return null;
    }

    public double getTotalWorkTime() {
        return totalWorkTime;
    }

    public int getOrderAmount() {
        return this.orderAmount;
    }

    public int getCurrentOrderId() {
        return (currentOrder != null ? currentOrder.getId() : 0);
    }

    public double getCourierLoadPercentage(double simulationTime) {
        return totalWorkTime == 0 ? 0 : (totalWorkTime / simulationTime) * 100;
    }

}