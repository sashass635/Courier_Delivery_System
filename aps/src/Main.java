import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите режим работы (automatic, step):");
        String mode = sc.nextLine().toLowerCase();

        switch (mode) {
            case "automatic":
                Model automaticModel = new Model(3, 10, 0.5, 3);
                automaticModel.runSimulation(1000);
                break;

            case "step":
                Model stepModel = new Model(1, 3, 0.5, 3);
                stepModel.runStepByStep(1000);
                break;

            default:
                System.out.println("Некорректный ввод данных, попробуйте снова.");
        }

        sc.close();
    }
}
