public class Shirts extends Clothes {
    private String sleeveType;

    public Shirts(String name, String size, double price, int quantity, String material, String sleeveType) {
        super(name, size, price, quantity, material);
        setSleeveType(sleeveType);
    }

    public String getSleeveType() { return sleeveType; }

    public void setSleeveType(String sleeveType) {
        if (sleeveType == null || (!sleeveType.equals("короткий") && !sleeveType.equals("довгий"))) {
            throw new IllegalArgumentException("Тип рукава має бути: короткий або довгий");
        }
        this.sleeveType = sleeveType;
    }

    @Override
    public String toString() {
        return super.toString() + ", Рукав: " + sleeveType;
    }
}