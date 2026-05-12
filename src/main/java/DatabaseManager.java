import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String configFile) throws IOException, SQLException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(configFile)) {
            props.load(fis);
        }
        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");
        connection = DriverManager.getConnection(url, user, password);
        System.out.println("Підключення до БД успішне!");
    }

    public void saveClothes(Clothes clothes) {
        String sql = "INSERT INTO clothes (type, name, size, price, quantity, material, length, sleeve_type, season, has_hood, shoe_size, shoe_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, getType(clothes));
            pstmt.setString(2, clothes.getName());
            pstmt.setString(3, clothes.getSize());
            pstmt.setDouble(4, clothes.getPrice());
            pstmt.setInt(5, clothes.getQuantity());
            pstmt.setString(6, clothes.getMaterial());

            if (clothes instanceof Pants) {
                Pants p = (Pants) clothes;
                pstmt.setString(7, p.getLength());
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setNull(9, Types.VARCHAR);
                pstmt.setNull(10, Types.BOOLEAN);
                pstmt.setNull(11, Types.INTEGER);
                pstmt.setNull(12, Types.VARCHAR);
            } else if (clothes instanceof Shirts) {
                Shirts s = (Shirts) clothes;
                pstmt.setNull(7, Types.VARCHAR);
                pstmt.setString(8, s.getSleeveType());
                pstmt.setNull(9, Types.VARCHAR);
                pstmt.setNull(10, Types.BOOLEAN);
                pstmt.setNull(11, Types.INTEGER);
                pstmt.setNull(12, Types.VARCHAR);
            } else if (clothes instanceof Jacket) {
                Jacket j = (Jacket) clothes;
                pstmt.setNull(7, Types.VARCHAR);
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setString(9, j.getSeason());
                pstmt.setBoolean(10, j.isHasHood());
                pstmt.setNull(11, Types.INTEGER);
                pstmt.setNull(12, Types.VARCHAR);
            } else if (clothes instanceof Shoes) {
                Shoes sh = (Shoes) clothes;
                pstmt.setNull(7, Types.VARCHAR);
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setNull(9, Types.VARCHAR);
                pstmt.setNull(10, Types.BOOLEAN);
                pstmt.setInt(11, sh.getShoeSize());
                pstmt.setString(12, sh.getShoeType());
            } else {
                pstmt.setNull(7, Types.VARCHAR);
                pstmt.setNull(8, Types.VARCHAR);
                pstmt.setNull(9, Types.VARCHAR);
                pstmt.setNull(10, Types.BOOLEAN);
                pstmt.setNull(11, Types.INTEGER);
                pstmt.setNull(12, Types.VARCHAR);
            }
            pstmt.executeUpdate();
            System.out.println("Об'єкт збережено в БД!");
        } catch (SQLException e) {
            System.out.println("Помилка збереження: " + e.getMessage());
        }
    }

    private String getType(Clothes clothes) {
        if (clothes instanceof Pants) return "Pants";
        if (clothes instanceof Shirts) return "Shirts";
        if (clothes instanceof Jacket) return "Jacket";
        if (clothes instanceof Shoes) return "Shoes";
        return "Clothes";
    }

    public void close() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}