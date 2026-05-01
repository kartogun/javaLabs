/**
 * Клас для представлення одягу.
 *
 * @author Lobanov
 * @version 3.0
 */
public class Clothes {
    private static int totalCount = 0;

    private String name;
    private Size size;
    private double price;
    private int quantity;
    private String material;
    private Category category;

    // Конструктор з параметрами
    public Clothes(String name, Size size, double price, int quantity, String material, Category category) {
        setName(name);
        setSize(size);
        setPrice(price);
        setQuantity(quantity);
        setMaterial(material);
        setCategory(category);
        totalCount++;
    }

    // Конструктор копіювання
    public Clothes(Clothes other) {
        this(other.name, other.size, other.price, other.quantity, other.material, other.category);
    }

    // Статичний гетер
    public static int getTotalCount() {
        return totalCount;
    }

    // Гетери
    public String getName() { return name; }
    public Size getSize() { return size; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getMaterial() { return material; }
    public Category getCategory() { return category; }

    // Сетери з перевірками
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Назва не може бути порожньою");
        }
        this.name = name;
    }

    public void setSize(Size size) {
        if (size == null) {
            throw new IllegalArgumentException("Розмір не може бути null");
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

    public void setCategory(Category category) {
        if (category == null) {
            throw new IllegalArgumentException("Категорія не може бути null");
        }
        this.category = category;
    }

    @Override
    public String toString() {
        return "Одяг: " + name + ", Розмiр: " + size + ", Цiна: " + price + " грн, " +
                "Кiлькiсть: " + quantity + ", Матерiал: " + material + ", Категорiя: " + category;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Clothes clothes = (Clothes) obj;
        return Double.compare(clothes.price, price) == 0 &&
                quantity == clothes.quantity &&
                name.equals(clothes.name) &&
                size == clothes.size &&
                material.equals(clothes.material) &&
                category.getName().equals(clothes.category.getName());
    }
}