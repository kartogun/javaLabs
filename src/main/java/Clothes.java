import java.util.UUID;

public abstract class Clothes implements Comparable<Clothes>, Identifiable {
    protected UUID uuid;
    protected String name;
    protected String size;
    protected double price;
    protected int quantity;
    protected String material;

    public Clothes(String name, String size, double price, int quantity, String material) {
        this.uuid = UUID.randomUUID();
        setName(name);
        setSize(size);
        setPrice(price);
        setQuantity(quantity);
        setMaterial(material);
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() { return name; }
    public String getSize() { return size; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getMaterial() { return material; }

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
    public int compareTo(Clothes other) {
        return this.name.compareToIgnoreCase(other.name);
    }

    public String getShortInfo() {
        return name + " | UUID: " + uuid.toString().substring(0, 8) + "...";
    }

    @Override
    public String toString() {
        return "UUID: " + uuid + ", " + name + ", Розмiр: " + size + ", Цiна: " + price + " грн, Кiлькiсть: " + quantity + ", Матерiал: " + material;
    }
}