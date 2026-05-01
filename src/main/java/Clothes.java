public class Clothes {
    private String name;
    private String size;
    private double price;

    public Clothes(String name, String size, double price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public String getName() { return name; }
    public String getSize() { return size; }
    public double getPrice() { return price; }

    public void setName(String name) { this.name = name; }
    public void setSize(String size) { this.size = size; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Одяг: " + name + ", Розмiр: " + size + ", Цiна: " + price + " грн";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Clothes clothes = (Clothes) obj;
        return Double.compare(clothes.price, price) == 0 &&
                name.equals(clothes.name) &&
                size.equals(clothes.size);
    }
}