/**
 * Базовий клас для представлення одягу.
 *
 * @author Lobanov
 * @version 4.0
 */
public class Clothes {
    protected String name;
    protected String size;
    protected double price;
    protected int quantity;
    protected String material;

    // Конструктор
    public Clothes(String name, String size, double price, int quantity, String material) {
        setName(name);
        setSize(size);
        setPrice(price);
        setQuantity(quantity);
        setMaterial(material);
    }

    // Гетери
    public String getName() { return name; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getMaterial() { return material; }

    // Сетери з перевірками
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва не може бути порожньою");
        }
        this.name = name;
    }

    public void setSize(String size) {
        if (size == null || (!size.equals("S") && !size.equals("M") && !size.equals("L") && !size.equals("XL"))) {
            throw new IllegalArgumentException("Розмір має бути: S, M, L, XL");
        }
        this.size = size;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Ціна має бути більше 0");
        }
        this.price = price;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Кількість не може бути від'ємною");
        }
        this.quantity = quantity;
    }

    public void setMaterial(String material) {
        if (material == null || material.trim().isEmpty()) {
            throw new IllegalArgumentException("Матеріал не може бути порожнім");
        }
        this.material = material;
    }

    @Override
    public String toString() {
        return name + ", Розмiр: " + size + ", Цiна: " + price + " грн, Кiлькiсть: " + quantity + ", Матерiал: " + material;
    }
}