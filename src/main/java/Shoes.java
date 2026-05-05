/**
 * Клас для представлення взуття (похідний від Clothes).
 */
public class Shoes extends Clothes {
    private int shoeSize; // розмір взуття (36-46)
    private String shoeType; // тип: кросівки, черевики, туфлі

    public Shoes(String name, String size, double price, int quantity, String material,
                 int shoeSize, String shoeType) {
        super(name, size, price, quantity, material);
        setShoeSize(shoeSize);
        setShoeType(shoeType);
    }

    public int getShoeSize() { return shoeSize; }
    public String getShoeType() { return shoeType; }

    public void setShoeSize(int shoeSize) {
        if (shoeSize < 36 || shoeSize > 46) {
            throw new IllegalArgumentException("Розмір взуття має бути від 36 до 46");
        }
        this.shoeSize = shoeSize;
    }

    public void setShoeType(String shoeType) {
        if (shoeType == null || (!shoeType.equals("кросівки") && !shoeType.equals("черевики") && !shoeType.equals("туфлі"))) {
            throw new IllegalArgumentException("Тип взуття має бути: кросівки, черевики, туфлі");
        }
        this.shoeType = shoeType;
    }

    @Override
    public String toString() {
        return "Взуття: " + super.toString() + ", Розмір взуття: " + shoeSize + ", Тип: " + shoeType;
    }
}