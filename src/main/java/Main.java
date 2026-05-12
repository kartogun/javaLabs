import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static Store store;
    private static Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "input.txt";

    public static void main(String[] args) {
        loadStoreFromFile();

        if (store == null) {
            store = new Store("Новий магазин", "Нова адреса");
        }

        while (true) {
            System.out.println("\n=== МЕНЮ МАГАЗИНУ ===");
            System.out.println("1. Створити новий об'єкт");
            System.out.println("2. Вивести всі об'єкти");
            System.out.println("3. Модифікувати об'єкт");
            System.out.println("4. Видалити об'єкт");
            System.out.println("5. Пошук об'єкта");
            System.out.println("6. Вивести відсортовані об'єкти");
            System.out.println("7. Інформація про магазин");
            System.out.println("8. Пошук за UUID");
            System.out.println("9. Завершити роботу");
            System.out.print("Виберiть опцiю: ");

            int choice = readInt();

            switch (choice) {
                case 1: createObjectMenu(); break;
                case 2: printAllObjects(); break;
                case 3: modifyObject(); break;
                case 4: deleteObject(); break;
                case 5: searchMenu(); break;
                case 6: sortMenu(); break;
                case 7: System.out.println(store); break;
                case 8: searchByUuid(); break;
                case 9:
                    saveStoreToFile();
                    System.out.println("До побачення!");
                    scanner.close();
                    return;
                default: System.out.println("Некоректний вибiр.");
            }
        }
    }

    // ========== МОДИФІКАЦІЯ ОБ'ЄКТА ==========

    private static void modifyObject() {
        ArrayList<Clothes> list = store.getClothesList();
        if (list.isEmpty()) {
            System.out.println("Список порожнiй. Немає об'єктів для модифікації.");
            return;
        }

        System.out.println("\n=== Виберiть об'єкт для модифікації ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getShortInfo());
        }

        int index = readInt() - 1;
        if (index < 0 || index >= list.size()) {
            System.out.println("Некоректний вибiр!");
            return;
        }

        Clothes oldObject = list.get(index);
        System.out.println("\nВибрано: " + oldObject);

        System.out.println("\n--- Який атрибут хочете змінити? ---");
        System.out.println("1. Назва");
        System.out.println("2. Розмір");
        System.out.println("3. Ціна");
        System.out.println("4. Кількість");
        System.out.println("5. Матеріал");
        System.out.print("Ваш вибiр: ");

        int attrChoice = readInt();

        String newName = oldObject.getName();
        String newSize = oldObject.getSize();
        double newPrice = oldObject.getPrice();
        int newQuantity = oldObject.getQuantity();
        String newMaterial = oldObject.getMaterial();

        try {
            switch (attrChoice) {
                case 1:
                    newName = readString("Нова назва: ");
                    break;
                case 2:
                    newSize = readSize();
                    break;
                case 3:
                    newPrice = readDouble("Нова цiна: ");
                    break;
                case 4:
                    newQuantity = readInt("Нова кiлькiсть: ");
                    break;
                case 5:
                    newMaterial = readString("Новий матерiал: ");
                    break;
                default:
                    System.out.println("Некоректний вибiр!");
                    return;
            }

            Clothes newObject = createClothesFromData(
                    newName, newSize, newPrice, newQuantity, newMaterial,
                    oldObject instanceof Pants,
                    oldObject instanceof Shirts,
                    oldObject instanceof Jacket,
                    oldObject instanceof Shoes,
                    oldObject
            );

            if (store.update(oldObject, newObject)) {
                System.out.println("Об'єкт успiшно оновлено!");
            } else {
                System.out.println("Помилка: об'єкт не знайдено!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static Clothes createClothesFromData(String name, String size, double price, int quantity, String material,
                                                 boolean isPants, boolean isShirts, boolean isJacket, boolean isShoes,
                                                 Clothes original) {
        if (isPants) {
            String length = "";
            if (original instanceof Pants) length = ((Pants) original).getLength();
            return new Pants(name, size, price, quantity, material, length);
        } else if (isShirts) {
            String sleeve = "";
            if (original instanceof Shirts) sleeve = ((Shirts) original).getSleeveType();
            return new Shirts(name, size, price, quantity, material, sleeve);
        } else if (isJacket) {
            String season = "";
            boolean hasHood = false;
            if (original instanceof Jacket) {
                season = ((Jacket) original).getSeason();
                hasHood = ((Jacket) original).isHasHood();
            }
            return new Jacket(name, size, price, quantity, material, season, hasHood);
        } else if (isShoes) {
            int shoeSize = 40;
            String shoeType = "";
            if (original instanceof Shoes) {
                shoeSize = ((Shoes) original).getShoeSize();
                shoeType = ((Shoes) original).getShoeType();
            }
            return new Shoes(name, size, price, quantity, material, shoeSize, shoeType);
        } else {
            return new Clothes(name, size, price, quantity, material) {};
        }
    }

    // ========== ВИДАЛЕННЯ ОБ'ЄКТА ==========

    private static void deleteObject() {
        ArrayList<Clothes> list = store.getClothesList();
        if (list.isEmpty()) {
            System.out.println("Список порожнiй. Немає об'єктів для видалення.");
            return;
        }

        System.out.println("\n=== Виберiть об'єкт для видалення ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getShortInfo());
        }

        int index = readInt() - 1;
        if (index < 0 || index >= list.size()) {
            System.out.println("Некоректний вибiр!");
            return;
        }

        Clothes selected = list.get(index);
        System.out.println("Вибрано: " + selected);
        System.out.print("Ви дiйсно хочете видалити цей об'єкт? (так/ні): ");
        String confirm = scanner.nextLine().toLowerCase();

        if (confirm.equals("так") || confirm.equals("yes") || confirm.equals("y")) {
            if (store.delete(selected)) {
                System.out.println("Об'єкт успiшно видалено!");
            } else {
                System.out.println("Помилка: об'єкт не знайдено!");
            }
        } else {
            System.out.println("Видалення скасовано.");
        }
    }

    // ========== ПОШУК ЗА UUID ==========

    private static void searchByUuid() {
        System.out.print("Введiть UUID для пошуку: ");
        String uuidStr = scanner.nextLine();
        try {
            UUID uuid = UUID.fromString(uuidStr);
            Clothes found = store.findByUuid(uuid);
            if (found != null) {
                System.out.println("\n=== ЗНАЙДЕНО ОБ'ЄКТ ===");
                System.out.println(found);
            } else {
                System.out.println("Об'єкт з таким UUID не знайдено.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: Некоректний формат UUID!");
        }
    }

    // ========== МЕНЮ СОРТУВАННЯ ==========

    private static void sortMenu() {
        while (true) {
            System.out.println("\n--- Виберiть критерiй сортування ---");
            System.out.println("1. За назвою (за зростанням)");
            System.out.println("2. За цiною (вiд дешевших до дорожчих)");
            System.out.println("3. За кiлькiстю (вiд меншої до бiльшої)");
            System.out.println("0. Повернутися");
            System.out.print("Ваш вибiр: ");

            int choice = readInt();

            switch (choice) {
                case 1: sortByName(); return;
                case 2: sortByPrice(); return;
                case 3: sortByQuantity(); return;
                case 0: return;
                default: System.out.println("Некоректний вибiр.");
            }
        }
    }

    private static void sortByName() {
        ArrayList<Clothes> list = store.getClothesList();
        if (list.isEmpty()) { System.out.println("Список порожнiй."); return; }
        Comparator<Clothes> cmp = (o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName());
        ArrayList<Clothes> sorted = new ArrayList<>(list);
        Collections.sort(sorted, cmp);
        System.out.println("\n=== ВІДСОРТОВАНО ЗА НАЗВОЮ ===");
        for (int i = 0; i < sorted.size(); i++) {
            System.out.println((i + 1) + ". " + sorted.get(i));
        }
    }

    private static void sortByPrice() {
        ArrayList<Clothes> list = store.getClothesList();
        if (list.isEmpty()) { System.out.println("Список порожнiй."); return; }
        Comparator<Clothes> cmp = (o1, o2) -> Double.compare(o1.getPrice(), o2.getPrice());
        ArrayList<Clothes> sorted = new ArrayList<>(list);
        Collections.sort(sorted, cmp);
        System.out.println("\n=== ВІДСОРТОВАНО ЗА ЦІНОЮ ===");
        for (int i = 0; i < sorted.size(); i++) {
            System.out.println((i + 1) + ". " + sorted.get(i));
        }
    }

    private static void sortByQuantity() {
        ArrayList<Clothes> list = store.getClothesList();
        if (list.isEmpty()) { System.out.println("Список порожнiй."); return; }
        Comparator<Clothes> cmp = (o1, o2) -> Integer.compare(o1.getQuantity(), o2.getQuantity());
        ArrayList<Clothes> sorted = new ArrayList<>(list);
        Collections.sort(sorted, cmp);
        System.out.println("\n=== ВІДСОРТОВАНО ЗА КІЛЬКІСТЮ ===");
        for (int i = 0; i < sorted.size(); i++) {
            System.out.println((i + 1) + ". " + sorted.get(i));
        }
    }

    // ========== ІНШІ МЕТОДИ ==========

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
                case "Clothes": return new Clothes(name, size, price, quantity, material) {};
                case "Pants": return new Pants(name, size, price, quantity, material, parts[5]);
                case "Shirts": return new Shirts(name, size, price, quantity, material, parts[5]);
                case "Jacket": return new Jacket(name, size, price, quantity, material, parts[5], Boolean.parseBoolean(parts[6]));
                case "Shoes": return new Shoes(name, size, price, quantity, material, Integer.parseInt(parts[5]), parts[6]);
                default: return null;
            }
        } catch (Exception e) { return null; }
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
        printResults(store.searchByName(scanner.nextLine()), "назвою");
    }

    private static void searchByMaxPrice() {
        System.out.print("Введiть макс. цiну: ");
        printResults(store.searchByMaxPrice(readDouble("Введiть макс. цiну: ")), "цiною");
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
                System.out.println((i + 1) + ". " + results.get(i));
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

    private static void createClothes() {
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        int quantity = readInt("Кiлькiсть: ");
        store.addNewClothes(new Clothes(name, size, price, quantity, material) {}, quantity);
    }

    private static void createPants() {
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        String length = readString("Довжина (короткі/довгі): ");
        int quantity = readInt("Кiлькiсть: ");
        store.addNewClothes(new Pants(name, size, price, quantity, material, length), quantity);
    }

    private static void createShirts() {
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        String sleeve = readString("Рукав (короткий/довгий): ");
        int quantity = readInt("Кiлькiсть: ");
        store.addNewClothes(new Shirts(name, size, price, quantity, material, sleeve), quantity);
    }

    private static void createJacket() {
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        String season = readString("Сезон (зимова/осіння/літня): ");
        boolean hasHood = readBoolean("Капюшон");
        int quantity = readInt("Кiлькiсть: ");
        store.addNewClothes(new Jacket(name, size, price, quantity, material, season, hasHood), quantity);
    }

    private static void createShoes() {
        String name = readString("Назва: ");
        String size = readSize();
        double price = readDouble("Цiна: ");
        String material = readString("Матерiал: ");
        int shoeSize = Integer.parseInt(readString("Розмiр взуття (36-46): "));
        String shoeType = readString("Тип (кросівки/черевики/туфлі): ");
        int quantity = readInt("Кiлькiсть: ");
        store.addNewClothes(new Shoes(name, size, price, quantity, material, shoeSize, shoeType), quantity);
    }

    private static void printAllObjects() {
        ArrayList<Clothes> list = store.getClothesList();
        if (list.isEmpty()) {
            System.out.println("Список порожнiй");
            return;
        }
        System.out.println("\n=== ВСІ ТОВАРИ ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
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
}