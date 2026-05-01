import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Тести для класу Clothes.
 *
 * @author Студент
 * @version 2.0
 */
public class ClothesTest {

    private Category createTestCategory() {
        return new Category("Тест", "Тестова категорія");
    }

    @Test
    void testConstructorThrowsExceptionWhenNameEmpty() {
        Category category = createTestCategory();
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("", Size.M, 100, 5, "Бавоина", category);
        });
    }

    @Test
    void testSetterThrowsExceptionWhenPriceNegative() {
        Category category = createTestCategory();
        Clothes clothes = new Clothes("Футболка", Size.M, 100, 5, "Бавоина", category);
        assertThrows(IllegalArgumentException.class, () -> {
            clothes.setPrice(-50);
        });
    }

    @Test
    void testConstructorThrowsExceptionWhenSizeInvalid() {
        Category category = createTestCategory();
        // Size тепер enum, тому некоректний розмір передати не можна
        // Перевіряємо що null викликає виняток
        assertThrows(IllegalArgumentException.class, () -> {
            new Clothes("Штани", null, 200, 3, "Джинс", category);
        });
    }

    @Test
    void testSetterThrowsExceptionWhenQuantityNegative() {
        Category category = createTestCategory();
        Clothes clothes = new Clothes("Сорочка", Size.L, 150, 2, "Шовк", category);
        assertThrows(IllegalArgumentException.class, () -> {
            clothes.setQuantity(-1);
        });
    }

    @Test
    void testCopyConstructor() {
        Category category = createTestCategory();
        Clothes original = new Clothes("Куртка", Size.XL, 500, 3, "Шкiра", category);
        Clothes copy = new Clothes(original);

        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getSize(), copy.getSize());
        assertEquals(original.getPrice(), copy.getPrice());
        assertEquals(original.getQuantity(), copy.getQuantity());
        assertEquals(original.getMaterial(), copy.getMaterial());
    }

    @Test
    void testStaticCounter() {
        int before = Clothes.getTotalCount();
        Category category = createTestCategory();
        new Clothes("Тест", Size.S, 10, 1, "Тест", category);
        new Clothes("Тест2", Size.M, 20, 2, "Тест2", category);
        int after = Clothes.getTotalCount();

        assertEquals(before + 2, after);
    }
}