import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Store store;
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "input.txt";
    private static DatabaseManager dbManager;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Вкажіть шлях до конфігураційного файлу!");
            System.out.println("Приклад: java Main db.properties");
            return;
        }

        try {
            dbManager = new DatabaseManager(args[0]);
        } catch (Exception e) {
            System.out.println("Помилка підключення до БД: " + e.getMessage());
        }

        loadStoreFromFile();

        if (store == null) {
            store = new Store("Новий магазин", "Нова адреса");
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
                case 1: createObjectMenu(); break;
                case 2: printAllObjects(); break;
                case 3: searchMenu(); break;
                case 4: addExistingProduct(); break;
                case 5: System.out.println(store); break;
                case 6:
                    saveStoreToFile();
                    if (dbManager != null) dbManager.close();
                    System.out.println("До побачення!");
                    scanner.close();
                    return;
                default: System.out.println("Некоректний вибiр.");
            }
        }
    }

    private static void loadStoreFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            store = new Store("Новий магазин", "Нова адреса");
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            String storeName = fileScanner.nextLine();
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
            System.out.println("Файл не знайдено");
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
                case "Clothes": return new Clothes(name, size, price, quantity, material);
                case "Pants": return new Pants(name, size, price, quantity, material, parts[5]);
                case "Shirts": return new Shirts(name, size, price, quantity, material, parts[5]);
                case "Jacket": return new Jacket(name, size, price, quantity, material, parts[5], Boolean.parseBoolean(parts[6]));
                case "Shoes": return new Shoes(name, size, price, quantity, material, Integer.parseInt(parts[5]), parts[6]);
                default: return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static void saveStoreToFile() {
        try (PrintWriter writer = new PrintWriter(FILE_NAME)) {
            writer.println(store.getStoreName());
            writer.println(store.getAddress());
            for (Clothes item : store.getClothesList()) {
                writer.println(objectToString(item) + "|" + item.getQuantity());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Помилка запису");
        }
    }

    private static String objectToString(Clothes obj) {
        if (obj instanceof Pants) {
            Pants p = (Pants) obj;
            return "Pants|" + p.getName() + "|" + p.getSize() + "|" + p.getPrice() + "|" + p.getMaterial() + "|" + p.getLength();
        } else if (obj instanceof Shirts) {
            Shirts s = (Shirts) obj;
            return "Shirts|" + s.getName() + "|" + s.getSize() + "|" + s.getPrice() + "|" + s.getMaterial() + "|" + s.getSleeveType();
        } else if (obj instanceof Jacket) {
            Jacket j = (Jacket) obj;
            return "Jacket|" + j.getName() + "|" + j.getSize() + "|" + j.getPrice() + "|" + j.getMaterial() + "|" + j.getSeason() + "|" + j.isHasHood();
        } else if (obj instanceof Shoes) {
            Shoes sh = (Shoes) obj;
            return "Shoes|" + sh.getName() + "|" + sh.getSize() + "|" + sh.getPrice() + "|" + sh.getMaterial() + "|" + sh.getShoeSize() + "|" + sh.getShoeType();
        } else {
            return "Clothes|" + obj.getName() + "|" + obj.getSize() + "|" + obj.getPrice() + "|" + obj.getMaterial();
        }
    }

    private static void addExistingProduct() {
        if (store.getClothesList().isEmpty()) {
            System.out.println("Список порожнiй");
            return;
        }
        printAllObjects();
        System.out.print("Виберiть номер: ");
        int index = readInt() - 1;
        if (index >= 0 && index < store.getClothesList().size()) {
            int quantity = readInt("Введiть кiлькiсть: ");
            store.addNewClothes(store.getClothesList().get(index), quantity);
        }
    }

    private static void searchMenu() {
        while (true) {
            System.out.println("\n--- Пошук ---");
            System.out.println("1. За назвою");
            System.out.println("2. За цiною (не бiльше)");
            System.out.println("3. За розмiром");
            System.out.println("0. Назад");
            int choice = readInt();
            switch (choice) {
                case 1: searchByName(); break;
                case 2: searchByMaxPrice(); break;
                case 3: searchBySize(); break;
                case 0: return;
                default: System.out.println("Некоректний вибiр");
            }
        }
    }

    private static void searchByName() {
        System.out.print("Введiть назву: ");
        ArrayList<Clothes> results = store.searchByName(scanner.nextLine());
        printResults(results, "назвою");
    }

    private static void searchByMaxPrice() {
        double price = readDouble("Введiть макс. цiну: ");
        printResults(store.searchByMaxPrice(price), "цiною");
    }

    private static void searchBySize() {
        String size = readSize();
        printResults(store.searchBySize(size), "розмiром");
    }

    private static void printResults(ArrayList<Clothes> results, String criteria) {
        if (results.isEmpty()) {
            System.out.println("Не знайдено");
        } else {
            System.out.println("Знайдено " + results.size() + " об'єкт(ів):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i+1) + ". " + results.get(i));
            }
        }
    }

    private static void createObjectMenu() {
        System.out.println("\n--- Тип об'єкта ---");
        System.out.println("1. Одяг");
        System.out.println("2. Штани");
        System.out.println("3. Сорочка");
        System.out.println("4. Куртка");
        System.out.println("5. Взуття");
        System.out.println("0. Назад");
        int type = readInt();
        switch (type) {
            case 1: createClothes(); break;
            case 2: createPants(); break;
            case 3: createShirts(); break;
            case 4: createJacket(); break;
            case 5: createShoes(); break;
            case 0: return;
        }
    }

    private static int readInt() {
        while (true) {
            try { return Integer.parseInt(scanner.nextLine()); }
            catch (NumberFormatException e) { System.out.print("Введiть число: "); }
        }
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        return readInt();
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Double.parseDouble(scanner.nextLine()); }
            catch (NumberFormatException e) { System.out.println("Введiть число!"); }
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (!input.trim().isEmpty()) return input;
            System.out.println("Не може бути порожнiм!");
        }
    }

    private static String readSize() {
        while (true) {
            System.out.print("Розмiр (S/M/L/XL): ");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("S") || input.equals("M") || input.equals("L") || input.equals("XL")) return input;
            System.out.println("Некоректний розмiр");
        }
    }

    private static boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt + " (так/ні): ");
            String input = scanner.nextLine().toLowerCase();
            if (input.equals("так")) return true;
            if (input.equals("ні")) return false;
            System.out.println("Введiть так або нi");
        }
    }

    private static void createClothes() {
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        int quantity = readInt("Кiлькiсть: ");
        Clothes c = new Clothes(name, size, price, quantity, material);
        store.addNewClothes(c, quantity);
        if (dbManager != null) dbManager.saveClothes(c);
    }

    private static void createPants() {
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        String length = readString("Довжина (короткі/довгі): ");
        int quantity = readInt("Кiлькiсть: ");
        Pants p = new Pants(name, size, price, quantity, material, length);
        store.addNewClothes(p, quantity);
        if (dbManager != null) dbManager.saveClothes(p);
    }

    private static void createShirts() {
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        String sleeve = readString("Рукав (короткий/довгий): ");
        int quantity = readInt("Кiлькiсть: ");
        Shirts s = new Shirts(name, size, price, quantity, material, sleeve);
        store.addNewClothes(s, quantity);
        if (dbManager != null) dbManager.saveClothes(s);
    }

    private static void createJacket() {
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        String season = readString("Сезон (зимова/осіння/літня): ");
        boolean hasHood = readBoolean("Капюшон");
        int quantity = readInt("Кiлькiсть: ");
        Jacket j = new Jacket(name, size, price, quantity, material, season, hasHood);
        store.addNewClothes(j, quantity);
        if (dbManager != null) dbManager.saveClothes(j);
    }

    private static void createShoes() {
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        int shoeSize = readInt("Розмiр взуття (36-46): ");
        String shoeType = readString("Тип (кросівки/черевики/туфлі): ");
        int quantity = readInt("Кiлькiсть: ");
        Shoes sh = new Shoes(name, size, price, quantity, material, shoeSize, shoeType);
        store.addNewClothes(sh, quantity);
        if (dbManager != null) dbManager.saveClothes(sh);
    }

    private static void printAllObjects() {
        ArrayList<Clothes> list = store.getClothesList();
        if (list.isEmpty()) {
            System.out.println("Список порожнiй");
            return;
        }
        System.out.println("\n=== ВСІ ТОВАРИ ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i+1) + ". " + list.get(i));
        }
    }
}