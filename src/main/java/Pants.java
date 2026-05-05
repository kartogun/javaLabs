/**
 * Клас для представлення штанів (похідний від Clothes).
 */
public class Pants extends Clothes {
    private String length; // довжина: короткі, довгі

    public Pants(String name, String size, double price, int quantity, String material, String length) {
        super(name, size, price, quantity, material);
        setLength(length);
    }

    public String getLength() { return length; }

    public void setLength(String length) {
        if (length == null || (!length.equals("короткі") && !length.equals("довгі"))) {
            throw new IllegalArgumentException("Довжина має бути: короткі або довгі");
        }
        this.length = length;
    }

    @Override
    public String toString() {
        return "Штани: " + super.toString() + ", Довжина: " + length;
    }
}