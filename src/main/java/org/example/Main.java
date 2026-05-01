import java.util.ArrayList;
import java.util.Scanner;

/**
 * Драйвер-клас з консольним меню.
 *
 * @author Lobanov
 * @version 3.0
 */
public class Main {
    private static ArrayList<Clothes> clothesList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Демонстрація статичного лічильника
        System.out.println("=== Демонстрація статичного лічильника ===");
        System.out.println("Кількість створених об'єктів: " + Clothes.getTotalCount());

        while (true) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести інформацію про всі об'єкти");
            System.out.println("3. Створити копію об'єкта");
            System.out.println("4. Показати кількість об'єктів");
            System.out.println("5. Завершити роботу");
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
                    copyObject();
                    break;
                case 4:
                    System.out.println("Всього створено об'єктів: " + Clothes.getTotalCount());
                    break;
                case 5:
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

    private static Size readSize() {
        while (true) {
            System.out.print("Розмiр (S/M/L/XL): ");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("S") || input.equals("M") || input.equals("L") || input.equals("XL")) {
                return Size.valueOf(input);
            }
            System.out.println("Некоректний розмiр. Введiть S, M, L або XL");
        }
    }

    private static void createObject() {
        System.out.println("\n--- Створення нового одягу ---");

        String name = readString("Назва: ");
        Size size = readSize();
        double price = readDouble("Цiна: ");
        int quantity = readInt("Кiлькiсть: ");
        String material = readString("Матерiал: ");

        System.out.println("--- Категорiя ---");
        String catName = readString("Назва категорiї: ");
        String catDesc = readString("Опис категорiї: ");
        Category category = new Category(catName, catDesc);

        try {
            Clothes clothes = new Clothes(name, size, price, quantity, material, category);
            clothesList.add(clothes);
            System.out.println("Об'єкт успiшно створено!");
            System.out.println("Всього створено об'єктів: " + Clothes.getTotalCount());
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void copyObject() {
        if (clothesList.isEmpty()) {
            System.out.println("Список порожнiй. Спочатку створіть хоча б один об'єкт.");
            return;
        }

        printAllObjects();
        System.out.print("Виберiть номер об'єкта для копіювання: ");
        int index = readInt() - 1;

        if (index >= 0 && index < clothesList.size()) {
            Clothes original = clothesList.get(index);
            Clothes copy = new Clothes(original);
            clothesList.add(copy);
            System.out.println("Копiю створено!");
            System.out.println("Всього створено об'єктів: " + Clothes.getTotalCount());
        } else {
            System.out.println("Некоректний номер!");
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