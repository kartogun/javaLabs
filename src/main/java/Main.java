import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Драйвер-клас з консольним меню. Використовує клас Store.
 *
 * @author Lobanov
 * @version 8.0
 */
public class Main {
    private static Store store;
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        loadStoreFromFile();

        if (store == null) {
            System.out.println("Помилка: не вдалося завантажити магазин. Створюємо порожній.");
            store = new Store("Невідомий магазин", "Невідома адреса");
        }

        while (true) {
            System.out.println("\n=== МЕНЮ МАГАЗИНУ ===");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести всі об'єкти");
            System.out.println("3. Пошук об'єкта");
            System.out.println("4. Додати товар (за кількістю)");
            System.out.println("5. Інформація про магазин");
            System.out.println("6. Завершити роботу");
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
                    addExistingProduct();
                    break;
                case 5:
                    System.out.println(store);
                    break;
                case 6:
                    saveStoreToFile();
                    System.out.println("Дані збережено у файл " + FILE_NAME);
                    System.out.println("До побачення!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Некоректний вибiр.");
            }
        }
    }

    // ========== ЗАВАНТАЖЕННЯ ТА ЗБЕРЕЖЕННЯ ==========

    private static void loadStoreFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Файл " + FILE_NAME + " не знайдено. Створюємо порожній магазин.");
            store = new Store("Новий магазин", "Нова адреса");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            if (!fileScanner.hasNextLine()) return;
            String storeName = fileScanner.nextLine();

            if (!fileScanner.hasNextLine()) return;
            String address = fileScanner.nextLine();

            store = new Store(storeName, address);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.trim().isEmpty()) continue;

                Clothes obj = parseObjectFromLine(line);
                if (obj != null) {
                    store.addNewClothes(obj, obj.getQuantity());
                }
            }
            System.out.println("Завантажено магазин: " + store);

        } catch (FileNotFoundException e) {
            System.out.println("Помилка: файл не знайдено");
        } catch (Exception e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private static Clothes parseObjectFromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 5) return null;

        String type = parts[0];
        String name = parts[1];
        String size = parts[2];
        double price = Double.parseDouble(parts[3]);
        String material = parts[4];
        int quantity = 1;

        try {
            switch (type) {
                case "Clothes":
                    return new Clothes(name, size, price, quantity, material);
                case "Pants":
                    if (parts.length >= 6) {
                        return new Pants(name, size, price, quantity, material, parts[5]);
                    }
                    break;
                case "Shirts":
                    if (parts.length >= 6) {
                        return new Shirts(name, size, price, quantity, material, parts[5]);
                    }
                    break;
                case "Jacket":
                    if (parts.length >= 7) {
                        boolean hasHood = Boolean.parseBoolean(parts[6]);
                        return new Jacket(name, size, price, quantity, material, parts[5], hasHood);
                    }
                    break;
                case "Shoes":
                    if (parts.length >= 7) {
                        int shoeSize = Integer.parseInt(parts[5]);
                        return new Shoes(name, size, price, quantity, material, shoeSize, parts[6]);
                    }
                    break;
            }
        } catch (Exception e) {
            System.out.println("Помилка парсингу рядка: " + line);
        }
        return null;
    }

    private static void saveStoreToFile() {
        if (store == null) return;

        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            writer.println(store.getStoreName());
            writer.println(store.getAddress());

            for (Clothes item : store.getClothesList()) {
                writer.println(objectToString(item) + "|" + item.getQuantity());
            }
            System.out.println("Збережено магазин та " + store.getClothesList().size() + " товарів");
        } catch (FileNotFoundException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    private static String objectToString(Clothes obj) {
        if (obj instanceof Pants) {
            Pants p = (Pants) obj;
            return "Pants|" + p.getName() + "|" + p.getSize() + "|" + p.getPrice() + "|" +
                    p.getMaterial() + "|" + p.getLength();
        } else if (obj instanceof Shirts) {
            Shirts s = (Shirts) obj;
            return "Shirts|" + s.getName() + "|" + s.getSize() + "|" + s.getPrice() + "|" +
                    s.getMaterial() + "|" + s.getSleeveType();
        } else if (obj instanceof Jacket) {
            Jacket j = (Jacket) obj;
            return "Jacket|" + j.getName() + "|" + j.getSize() + "|" + j.getPrice() + "|" +
                    j.getMaterial() + "|" + j.getSeason() + "|" + j.isHasHood();
        } else if (obj instanceof Shoes) {
            Shoes sh = (Shoes) obj;
            return "Shoes|" + sh.getName() + "|" + sh.getSize() + "|" + sh.getPrice() + "|" +
                    sh.getMaterial() + "|" + sh.getShoeSize() + "|" + sh.getShoeType();
        } else {
            return "Clothes|" + obj.getName() + "|" + obj.getSize() + "|" + obj.getPrice() + "|" +
                    obj.getMaterial();
        }
    }

    // ========== МЕТОДИ РОБОТИ З STORE ==========

    private static void addExistingProduct() {
        if (store.getClothesList().isEmpty()) {
            System.out.println("Список товарів порожній. Спочатку створіть хоча б один товар.");
            return;
        }

        printAllObjects();
        System.out.print("Виберiть номер товару для додавання: ");
        int index = readInt() - 1;

        if (index >= 0 && index < store.getClothesList().size()) {
            Clothes selected = store.getClothesList().get(index);
            int quantity = readInt("Введiть кiлькiсть для додавання: ");

            try {
                store.addNewClothes(selected, quantity);
            } catch (IllegalArgumentException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        } else {
            System.out.println("Некоректний номер!");
        }
    }

    // ========== МЕТОДИ ПОШУКУ ==========

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

    private static void searchByName() {
        System.out.print("Введiть назву для пошуку: ");
        String name = scanner.nextLine();
        ArrayList<Clothes> results = store.searchByName(name);
        printSearchResults(results, "назвою \"" + name + "\"");
    }

    private static void searchByMaxPrice() {
        double maxPrice = readDouble("Введiть максимальну цiну: ");
        ArrayList<Clothes> results = store.searchByMaxPrice(maxPrice);
        printSearchResults(results, "цiною не бiльше " + maxPrice);
    }

    private static void searchBySize() {
        String size = readSize();
        ArrayList<Clothes> results = store.searchBySize(size);
        printSearchResults(results, "розмiром " + size.toUpperCase());
    }

    private static void printSearchResults(ArrayList<Clothes> results, String criteria) {
        if (results.isEmpty()) {
            System.out.println("Об'єктiв за критерiєм \"" + criteria + "\" не знайдено.");
        } else {
            System.out.println("\nЗнайдено " + results.size() + " об'єкт(ів):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
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
        String material = readString("Матерiал: ");
        int quantity = readInt("Кiлькiсть: ");

        try {
            Clothes clothes = new Clothes(name, size, price, quantity, material);
            store.addNewClothes(clothes, quantity);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createPants() {
        System.out.println("\n--- Створення штанів ---");
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        String length = readString("Довжина (короткі/довгі): ");
        int quantity = readInt("Кiлькiсть: ");

        try {
            Pants pants = new Pants(name, size, price, quantity, material, length);
            store.addNewClothes(pants, quantity);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createShirts() {
        System.out.println("\n--- Створення сорочки ---");
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        String sleeve = readString("Рукав (короткий/довгий): ");
        int quantity = readInt("Кiлькiсть: ");

        try {
            Shirts shirts = new Shirts(name, size, price, quantity, material, sleeve);
            store.addNewClothes(shirts, quantity);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createJacket() {
        System.out.println("\n--- Створення куртки ---");
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        String season = readString("Сезон (зимова/осіння/літня): ");
        boolean hasHood = readBoolean("Капюшон");
        int quantity = readInt("Кiлькiсть: ");

        try {
            Jacket jacket = new Jacket(name, size, price, quantity, material, season, hasHood);
            store.addNewClothes(jacket, quantity);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void createShoes() {
        System.out.println("\n--- Створення взуття ---");
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        int shoeSize = readInt("Розмiр взуття (36-46): ");
        String shoeType = readString("Тип (кросівки/черевики/туфлі): ");
        int quantity = readInt("Кiлькiсть: ");

        try {
            Shoes shoes = new Shoes(name, size, price, quantity, material, shoeSize, shoeType);
            store.addNewClothes(shoes, quantity);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void printAllObjects() {
        ArrayList<Clothes> list = store.getClothesList();
        if (list.isEmpty()) {
            System.out.println("Список товарiв порожнiй.");
            return;
        }
        System.out.println("\n=== ВСІ ТОВАРИ В МАГАЗИНІ ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }
}