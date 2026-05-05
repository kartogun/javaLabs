import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Драйвер-клас з консольним меню, роботою з файлами та пошуком.
 *
 * @author Lobanov
 * @version 7.0
 */
public class Main {
    private static ArrayList<Clothes> clothesList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        loadFromFile();

        while (true) {
            System.out.println("\n=== МЕНЮ ===");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести всі об'єкти");
            System.out.println("3. Пошук об'єкта");
            System.out.println("4. Завершити роботу");
            System.out.print("Виберiть опцiю: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    createObjectMenu();
                    break;
                case 2:
                    printAllObjects();
                    break;
                case 3:
                    searchMenu();
                    break;
                case 4:
                    saveToFile();
                    System.out.println("Дані збережено у файл " + FILE_NAME);
                    System.out.println("До побачення!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Некоректний вибiр.");
            }
        }
    }

    // ========== ПОШУК ==========

    private static void searchMenu() {
        while (true) {
            System.out.println("\n--- Пошук об'єкта ---");
            System.out.println("1. Пошук за назвою");
            System.out.println("2. Пошук за ціною (не більше заданої)");
            System.out.println("3. Пошук за розміром");
            System.out.println("0. Повернутися до головного меню");
            System.out.print("Ваш вибiр: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    searchByName();
                    break;
                case 2:
                    searchByMaxPrice();
                    break;
                case 3:
                    searchBySize();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Некоректний вибiр.");
            }
        }
    }

    // Пошук за назвою
    private static void searchByName() {
        System.out.print("Введiть назву для пошуку: ");
        String name = scanner.nextLine().toLowerCase();

        ArrayList<Clothes> results = new ArrayList<>();
        for (Clothes item : clothesList) {
            if (item.getName().toLowerCase().contains(name)) {
                results.add(item);
            }
        }

        printSearchResults(results, "назвою \"" + name + "\"");
    }

    // Пошук за ціною (не більше заданої)
    private static void searchByMaxPrice() {
        double maxPrice = readDouble("Введiть максимальну цiну: ");

        ArrayList<Clothes> results = new ArrayList<>();
        for (Clothes item : clothesList) {
            if (item.getPrice() <= maxPrice) {
                results.add(item);
            }
        }

        printSearchResults(results, "цiною не бiльше " + maxPrice);
    }

    // Пошук за розміром
    private static void searchBySize() {
        String size = readSize();

        ArrayList<Clothes> results = new ArrayList<>();
        for (Clothes item : clothesList) {
            if (item.getSize().equalsIgnoreCase(size)) {
                results.add(item);
            }
        }

        printSearchResults(results, "розмiром " + size.toUpperCase());
    }

    // Виведення результатів пошуку
    private static void printSearchResults(ArrayList<Clothes> results, String criteria) {
        if (results.isEmpty()) {
            System.out.println("Об'єктiв за критерiєм \"" + criteria + "\" не знайдено.");
        } else {
            System.out.println("\nЗнайдено " + results.size() + " об'єкт(ів) за критерiєм \"" + criteria + "\":");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
    }

    // ========== РОБОТА З ФАЙЛОМ ==========

    private static void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Файл " + FILE_NAME + " не знайдено. Починаємо з порожнiм списком.");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) continue;

                Clothes obj = parseObject(line);
                if (obj != null) {
                    clothesList.add(obj);
                }
            }
            System.out.println("Завантажено " + clothesList.size() + " об'єкт(ів) з файлу " + FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("Помилка: файл не знайдено");
        } catch (Exception e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private static void saveToFile() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            for (Clothes item : clothesList) {
                writer.println(objectToString(item));
            }
            System.out.println("Збережено " + clothesList.size() + " об'єкт(ів) у файл " + FILE_NAME);
        } catch (FileNotFoundException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    private static String objectToString(Clothes obj) {
        if (obj instanceof Pants) {
            Pants p = (Pants) obj;
            return "Pants|" + p.getName() + "|" + p.getSize() + "|" + p.getPrice() + "|" +
                    p.getQuantity() + "|" + p.getMaterial() + "|" + p.getLength();
        } else if (obj instanceof Shirts) {
            Shirts s = (Shirts) obj;
            return "Shirts|" + s.getName() + "|" + s.getSize() + "|" + s.getPrice() + "|" +
                    s.getQuantity() + "|" + s.getMaterial() + "|" + s.getSleeveType();
        } else if (obj instanceof Jacket) {
            Jacket j = (Jacket) obj;
            return "Jacket|" + j.getName() + "|" + j.getSize() + "|" + j.getPrice() + "|" +
                    j.getQuantity() + "|" + j.getMaterial() + "|" + j.getSeason() + "|" + j.isHasHood();
        } else if (obj instanceof Shoes) {
            Shoes sh = (Shoes) obj;
            return "Shoes|" + sh.getName() + "|" + sh.getSize() + "|" + sh.getPrice() + "|" +
                    sh.getQuantity() + "|" + sh.getMaterial() + "|" + sh.getShoeSize() + "|" + sh.getShoeType();
        } else {
            return "Clothes|" + obj.getName() + "|" + obj.getSize() + "|" + obj.getPrice() + "|" +
                    obj.getQuantity() + "|" + obj.getMaterial();
        }
    }

    private static Clothes parseObject(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) return null;

        String type = parts[0];
        String name = parts[1];
        String size = parts[2];
        double price = Double.parseDouble(parts[3]);
        int quantity = Integer.parseInt(parts[4]);
        String material = parts[5];

        try {
            switch (type) {
                case "Clothes":
                    return new Clothes(name, size, price, quantity, material);
                case "Pants":
                    if (parts.length >= 7) {
                        return new Pants(name, size, price, quantity, material, parts[6]);
                    }
                    break;
                case "Shirts":
                    if (parts.length >= 7) {
                        return new Shirts(name, size, price, quantity, material, parts[6]);
                    }
                    break;
                case "Jacket":
                    if (parts.length >= 8) {
                        boolean hasHood = Boolean.parseBoolean(parts[7]);
                        return new Jacket(name, size, price, quantity, material, parts[6], hasHood);
                    }
                    break;
                case "Shoes":
                    if (parts.length >= 8) {
                        int shoeSize = Integer.parseInt(parts[6]);
                        return new Shoes(name, size, price, quantity, material, shoeSize, parts[7]);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Помилка парсингу рядка: " + line);
        }
        return null;
    }

    // ========== МЕТОДИ СТВОРЕННЯ ОБ'ЄКТІВ ==========

    private static void createObjectMenu() {
        System.out.println("\n--- Виберiть тип об'єкта ---");
        System.out.println("1. Одяг (базовий)");
        System.out.println("2. Штани");
        System.out.println("3. Сорочка");
        System.out.println("4. Куртка");
        System.out.println("5. Взуття");
        System.out.println("0. Повернутися до меню");
        System.out.print("Ваш вибiр: ");

        int type = readInt();

        switch (type) {
            case 1: createClothes(); break;
            case 2: createPants(); break;
            case 3: createShirts(); break;
            case 4: createJacket(); break;
            case 5: createShoes(); break;
            case 0: return;
            default: System.out.println("Некоректний вибiр.");
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

    private static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (так/ні): ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("так")) return true;
            if (input.equals("ні")) return false;
            System.out.println("Введiть 'так' або 'ні'");
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

    private static void createJacket() {
        System.out.println("\n--- Створення куртки ---");
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        int quantity = readInt("Кiлькiсть: ");
        String material = readString("Матерiал: ");
        String season = readString("Сезон (зимова/осіння/літня): ");
        boolean hasHood = readBoolean("Капюшон");

        try {
            clothesList.add(new Jacket(name, size, price, quantity, material, season, hasHood));
            System.out.println("Створено!");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createShoes() {
        System.out.println("\n--- Створення взуття ---");
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        int quantity = readInt("Кiлькiсть: ");
        String material = readString("Матерiал: ");
        int shoeSize = readInt("Розмiр взуття (36-46): ");
        String shoeType = readString("Тип (кросівки/черевики/туфлі): ");

        try {
            clothesList.add(new Shoes(name, size, price, quantity, material, shoeSize, shoeType));
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