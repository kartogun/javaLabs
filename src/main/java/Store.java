import java.util.ArrayList;

/**
 * Клас-контейнер Store (магазин) для зберігання одягу.
 */
public class Store {
    private String storeName;
    private String address;
    private ArrayList<Clothes> clothesList;

    // Конструктор
    public Store(String storeName, String address) {
        this.storeName = storeName;
        this.address = address;
        this.clothesList = new ArrayList<>();
    }

    // Гетери
    public String getStoreName() { return storeName; }
    public String getAddress() { return address; }
    public ArrayList<Clothes> getClothesList() { return clothesList; }

    // Сетери
    public void setStoreName(String storeName) {
        if (storeName == null || storeName.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва магазину не може бути порожньою");
        }
        this.storeName = storeName;
    }

    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Адреса не може бути порожньою");
        }
        this.address = address;
    }

    /**
     * Додає новий одяг в магазин.
     * Якщо такий самий об'єкт вже існує - збільшує кількість.
     */
    public void addNewClothes(Clothes cl, int quantity) {
        if (cl == null) {
            throw new IllegalArgumentException("Одяг не може бути null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути більше 0");
        }

        // Перевіряємо, чи вже є такий об'єкт
        for (Clothes item : clothesList) {
            if (item.equals(cl)) {
                // Збільшуємо кількість
                item.setQuantity(item.getQuantity() + quantity);
                System.out.println("Додано " + quantity + " до існуючого товару. Нова кількість: " + item.getQuantity());
                return;
            }
        }

        // Якщо не знайшли - додаємо новий
        cl.setQuantity(quantity);
        clothesList.add(cl);
        System.out.println("Додано новий товар: " + cl.getName() + ", кількість: " + quantity);
    }

    // ========== МЕТОДИ ПОШУКУ ==========

    public ArrayList<Clothes> searchByName(String name) {
        ArrayList<Clothes> results = new ArrayList<>();
        for (Clothes item : clothesList) {
            if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<Clothes> searchByMaxPrice(double maxPrice) {
        ArrayList<Clothes> results = new ArrayList<>();
        for (Clothes item : clothesList) {
            if (item.getPrice() <= maxPrice) {
                results.add(item);
            }
        }
        return results;
    }

    public ArrayList<Clothes> searchBySize(String size) {
        ArrayList<Clothes> results = new ArrayList<>();
        for (Clothes item : clothesList) {
            if (item.getSize().equalsIgnoreCase(size)) {
                results.add(item);
            }
        }
        return results;
    }

    @Override
    public String toString() {
        return "Магазин: " + storeName + ", Адреса: " + address + ", Кількість товарів: " + clothesList.size();
    }
}