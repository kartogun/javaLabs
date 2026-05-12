import java.util.ArrayList;
import java.util.Collections;

public class Store {
    private String storeName;
    private String address;
    private ArrayList<Clothes> clothesList;

    public Store(String storeName, String address) {
        this.storeName = storeName;
        this.address = address;
        this.clothesList = new ArrayList<>();
    }

    public String getStoreName() { return storeName; }
    public String getAddress() { return address; }
    public ArrayList<Clothes> getClothesList() { return clothesList; }

    public void addNewClothes(Clothes cl, int quantity) {
        if (cl == null) {
            throw new IllegalArgumentException("Одяг не може бути null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Кількість має бути більше 0");
        }

        for (Clothes item : clothesList) {
            if (item.getName().equals(cl.getName()) && item.getSize().equals(cl.getSize())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cl.setQuantity(quantity);
        clothesList.add(cl);
    }

    public ArrayList<Clothes> getSortedList() {
        ArrayList<Clothes> sorted = new ArrayList<>(clothesList);
        Collections.sort(sorted);
        return sorted;
    }

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