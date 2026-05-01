import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Clothes> clothesList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Завершити роботу");
            System.out.print("Виберiть опцiю: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    createObject();
                    break;
                case 2:
                    printAllObjects();
                    break;
                case 3:
                    System.out.println("До побачення!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Некоректний вибiр. Спробуйте ще раз.");
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

    private static void createObject() {
        System.out.println("\n--- Створення нового одягу ---");

        String name = readString("Назва: ");
        String size = readString("Розмiр (S/M/L/XL): ");
        double price = readDouble("Цiна: ");
        int quantity = readInt("Кiлькiсть: ");
        String material = readString("Матерiал: ");

        try {
            Clothes clothes = new Clothes(name, size, price, quantity, material);
            clothesList.add(clothes);
            System.out.println("Об'єкт успiшно створено!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void printAllObjects() {
        if (clothesList.isEmpty()) {
            System.out.println("Список порожнiй. Створiть хоча б один об'єкт.");
            return;
        }
        System.out.println("\n=== Список одягу ===");
        for (int i = 0; i < clothesList.size(); i++) {
            System.out.println((i + 1) + ". " + clothesList.get(i));
        }
    }
}