import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClothesTest {

    @Test
    void testConstructorThrowsExceptionWhenNameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("", "M", 100, 5, "Бавоина");
        });
    }

    @Test
    void testSetterThrowsExceptionWhenPriceNegative() {
        Clothes clothes = new Clothes("Футболка", "M", 100, 5, "Бавоина");
        assertThrows(IllegalArgumentException.class, () -> {
            clothes.setPrice(-50);
        });
    }

    @Test
    void testConstructorThrowsExceptionWhenSizeInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Штани", "XXL", 200, 3, "Джинс");
        });
    }

    @Test
    void testSetterThrowsExceptionWhenQuantityNegative() {
        Clothes clothes = new Clothes("Сорочка", "L", 150, 2, "Шовк");
        assertThrows(IllegalArgumentException.class, () -> {
            clothes.setQuantity(-1);
        });
    }
}