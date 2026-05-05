import java.util.ArrayList;
import java.util.Scanner;

/**
 * Драйвер-клас з консольним меню. Демонстрація поліморфізму.
 *
 * @author Студент
 * @version 4.0
 */
public class Main {
    private static ArrayList<Clothes> clothesList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Створити одяг (базовий)");
            System.out.println("2. Створити штани");
            System.out.println("3. Створити сорочку");
            System.out.println("4. Вивести всі об'єкти");
            System.out.println("5. Завершити роботу");
            System.out.print("Виберiть опцiю: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    createClothes();
                    break;
                case 2:
                    createPants();
                    break;
                case 3:
                    createShirts();
                    break;
                case 4:
                    printAllObjects();
                    break;
                case 5:
                    System.out.println("До побачення!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Некоректний вибiр.");
            }
        }
    }

    private static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Введiть коректне число: ");
            }
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input;
            }
            System.out.println("Поле не може бути порожнiм!");
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введiть коректне число!");
            }
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введiть коректне цiле число!");
            }
        }
    }

    private static String readSize() {
        while (true) {
            System.out.print("Розмiр (S/M/L/XL): ");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("S") || input.equals("M") || input.equals("L") || input.equals("XL")) {
                return input;
            }
            System.out.println("Некоректний розмiр.");
        }
    }

    private static void createClothes() {
        System.out.println("\n--- Створення одягу ---");
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        int quantity = readInt("Кiлькiсть: ");
        String material = readString("Матерiал: ");

        try {
            clothesList.add(new Clothes(name, size, price, quantity, material));
            System.out.println("Створено!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createPants() {
        System.out.println("\n--- Створення штанів ---");
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        int quantity = readInt("Кiлькiсть: ");
        String material = readString("Матерiал: ");
        String length = readString("Довжина (короткі/довгі): ");

        try {
            clothesList.add(new Pants(name, size, price, quantity, material, length));
            System.out.println("Створено!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createShirts() {
        System.out.println("\n--- Створення сорочки ---");
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        int quantity = readInt("Кiлькiсть: ");
        String material = readString("Матерiал: ");
        String sleeve = readString("Рукав (короткий/довгий): ");

        try {
            clothesList.add(new Shirts(name, size, price, quantity, material, sleeve));
            System.out.println("Створено!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void printAllObjects() {
        if (clothesList.isEmpty()) {
            System.out.println("Список порожнiй.");
            return;
        }
        System.out.println("\n=== ВСІ ОБ'ЄКТИ (поліморфізм) ===");
        for (int i = 0; i < clothesList.size(); i++) {
            System.out.println((i + 1) + ". " + clothesList.get(i));
        }
    }
}