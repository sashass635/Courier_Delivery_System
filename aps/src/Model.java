import java.util.*;
import Model.*;

public class Model {
    private final List<Courier> couriers;
    private final Buffer buffer;
    private final List<OrderGenerator> generators;
    private final DispatchSystem dispatchSystem;
    private final CourierManager courierManager;
    private final List<Order> orders;
    private double currentTime;

    public Model(int numCouriers, int bufferCapacity, double lambda, int numGenerators) {
        this.couriers = new ArrayList<>();
        for (int i = 0; i < numCouriers; i++) {
            couriers.add(new Courier(i + 1));
        }
        this.buffer = new Buffer(bufferCapacity);
        this.generators = new ArrayList<>();
        for (int i = 0; i < numGenerators; i++) {
            generators.add(new OrderGenerator(i + 1, lambda));
        }
        this.dispatchSystem = new DispatchSystem(buffer);
        this.courierManager = new CourierManager(couriers);
        this.orders = new ArrayList<>();
        this.currentTime = 0.0;
    }

    public void runStepByStep(double simulationTime) {
        Scanner scanner = new Scanner(System.in);
        while (currentTime < simulationTime) {
            System.out.println("Press 'n' to process the next step.");
            String input = scanner.nextLine();

            if (!input.equalsIgnoreCase("n")) {
                continue;
            }

            System.out.println("Current time: " + currentTime);

            for (OrderGenerator generator : generators) {
                if (currentTime >= generator.getNextOrderTime()) {
                    Order newOrder = generator.generateOrder(currentTime);
                    System.out.println("Заказ " + newOrder.getId() + " был сгенерирован.");
                    orders.add(newOrder);
                    dispatchSystem.addOrderToBuffer(newOrder); // Добавляем в буфер
                    generator.scheduleNextOrder(); // Планируем следующее время для заказа
                    System.out.println("Следующее время генерации заказа: " + generator.getNextOrderTime());
                    break;
                }
            }

            // Назначение курьеру нового заказа, если он не занят
            courierManager.assignOrderToCourier(buffer, currentTime);

            for (Courier courier : couriers) {
                if (courier.hasCompletedOrder(currentTime)) {
                    Order completedOrder = courier.releaseOrder();
                    System.out.println("Заказ " + completedOrder.getId() + " выполнен курьером " + courier.getId());
                }
            }

            // Увеличиваем время симуляции на 0.5
            currentTime += 1;

            stepByStepStats();
        }
        System.out.println("Simulation ended.");
    }

 private void stepByStepStats() {
        System.out.println("==== Step-By-Step Statistics ====");
        System.out.println("Buffer information:");
        buffer.info();

        System.out.println("\nGenerators information:");
        System.out.println("+----+-------------+");
        System.out.printf("| %-2s | %-11s |\n", "ID", "Total Orders");
        System.out.println("+----+-------------+");
        for (var generator : generators) {
            System.out.printf("| %-2d | %-11d |\n", generator.getId(), generator.generatedItemsAmount);
        }
        System.out.println("+----+-------------+");

        System.out.println("\nCouriers information:");
        System.out.println("+----+---------+-------------+-----------------+");
        System.out.printf("| %-2s | %-7s | %-11s | %-15s |\n", "ID", "Order ID", "Total Orders", "Total Work Time");
        System.out.println("+----+---------+-------------+-----------------+");
        for (var courier : couriers) {
            int currentOrderId = courier.getCurrentOrderId();
            System.out.printf("| %-2d | %-7s | %-11d | %-15.2f |\n",
                    courier.getId(),
                    (currentOrderId == 0 ? "None" : currentOrderId),
                    courier.getOrderAmount(),
                    courier.getTotalWorkTime());
        }
        System.out.println("+----+---------+-------------+-----------------+");
    }

    public void runSimulation(double simulationTime) {
        while (currentTime < simulationTime) {

            for (OrderGenerator generator : generators) {
                if (currentTime >= generator.getNextOrderTime()) {
                    Order newOrder = generator.generateOrder(currentTime);
                    orders.add(newOrder);
                    dispatchSystem.addOrderToBuffer(newOrder);
                    generator.scheduleNextOrder();
                }
            }

            courierManager.assignOrderToCourier(buffer, currentTime);

            for (Courier courier : couriers) {
                if (courier.hasCompletedOrder(currentTime)) {
                    Order completedOrder = courier.releaseOrder();
                }
            }

            currentTime += 1;
        }
        printSimulationResults();
    }

    private void printSimulationResults() {
        System.out.printf("Simulation ended at time %.2f \n", currentTime);
        System.out.println("Total number of generated orders: " + orders.size());

        couriersTable();
        generatorsTable();
    }

    private void couriersTable() {
        System.out.println("Stats for couriers:");
        System.out.println("+----+----------------------+-----------------+");
        System.out.println("| ID | Processed Orders     | Total Work Time |");
        System.out.println("+----+----------------------+-----------------+");
        for (Courier courier : couriers) {
            System.out.printf("| %-2d | %-19d | %-15.2f |\n",
                    courier.getId(),
                    courier.getOrderAmount(),
                    courier.getTotalWorkTime());
        }
        System.out.println("+----+----------------------+-----------------+");
    }

    private void generatorsTable() {
        System.out.println("Stats for order generators:");
        System.out.println("+----+--------------+");
        System.out.println("| ID | Total Orders |");
        System.out.println("+----+--------------+");
        for (OrderGenerator generator : generators) {
            System.out.printf("| %-2d | %-12d |\n",
                    generator.getId(),
                    generator.generatedItemsAmount,
                    generator.getRejectedOrders());
        }
        System.out.println("+----+--------------+");
    }
}
