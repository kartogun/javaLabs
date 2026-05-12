import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.UUID;

public class MainApp extends Application {
    private Store store;
    private ObservableList<String> shortInfoList;
    private ListView<String> listView;
    private TextArea resultArea;

    @Override
    public void start(Stage primaryStage) {
        store = new Store("Мій магазин", "вул. Центральна, 1");
        shortInfoList = FXCollections.observableArrayList();

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        // ========== Додавання об'єкта ==========
        GridPane addPane = new GridPane();
        addPane.setHgap(10);
        addPane.setVgap(5);
        addPane.setPadding(new Insets(10));

        TextField nameField = new TextField();
        TextField sizeField = new TextField();
        TextField priceField = new TextField();
        TextField materialField = new TextField();
        TextField quantityField = new TextField();
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Clothes", "Pants", "Shirts", "Jacket", "Shoes");
        typeBox.setValue("Clothes");

        addPane.add(new Label("Назва:"), 0, 0);
        addPane.add(nameField, 1, 0);
        addPane.add(new Label("Розмір (S/M/L/XL):"), 0, 1);
        addPane.add(sizeField, 1, 1);
        addPane.add(new Label("Ціна:"), 0, 2);
        addPane.add(priceField, 1, 2);
        addPane.add(new Label("Матеріал:"), 0, 3);
        addPane.add(materialField, 1, 3);
        addPane.add(new Label("Кількість:"), 0, 4);
        addPane.add(quantityField, 1, 4);
        addPane.add(new Label("Тип:"), 0, 5);
        addPane.add(typeBox, 1, 5);

        Button addButton = new Button("Додати");
        addPane.add(addButton, 1, 6);

        // ========== Список об'єктів ==========
        listView = new ListView<>();
        listView.setPrefHeight(200);

        // ========== Пошук за UUID ==========
        GridPane searchPane = new GridPane();
        searchPane.setHgap(10);
        searchPane.setVgap(5);
        searchPane.setPadding(new Insets(10));

        TextField uuidField = new TextField();
        Button searchButton = new Button("Знайти за UUID");
        resultArea = new TextArea();
        resultArea.setPrefHeight(150);
        resultArea.setEditable(false);

        searchPane.add(new Label("UUID:"), 0, 0);
        searchPane.add(uuidField, 1, 0);
        searchPane.add(searchButton, 2, 0);
        searchPane.add(resultArea, 0, 1, 3, 1);

        root.getChildren().addAll(
                new Label("=== Додавання об'єкта ==="),
                addPane,
                new Label("=== Список об'єктів (коротко) ==="),
                listView,
                new Label("=== Пошук за UUID ==="),
                searchPane
        );

        // ========== Дії ==========
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String size = sizeField.getText().toUpperCase();
                double price = Double.parseDouble(priceField.getText());
                String material = materialField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                String type = typeBox.getValue();

                Clothes clothes = null;
                switch (type) {
                    case "Clothes":
                        clothes = new Clothes(name, size, price, quantity, material) {};
                        break;
                    case "Pants":
                        String length = "";
                        clothes = new Pants(name, size, price, quantity, material, length);
                        break;
                    case "Shirts":
                        String sleeve = "";
                        clothes = new Shirts(name, size, price, quantity, material, sleeve);
                        break;
                    case "Jacket":
                        String season = "літня";
                        boolean hasHood = false;
                        clothes = new Jacket(name, size, price, quantity, material, season, hasHood);
                        break;
                    case "Shoes":
                        int shoeSize = 40;
                        String shoeType = "кросівки";
                        clothes = new Shoes(name, size, price, quantity, material, shoeSize, shoeType);
                        break;
                }

                if (clothes != null) {
                    store.addNewClothes(clothes, quantity);
                    updateShortList();
                    nameField.clear();
                    sizeField.clear();
                    priceField.clear();
                    materialField.clear();
                    quantityField.clear();
                }
            } catch (Exception ex) {
                resultArea.setText("Помилка: " + ex.getMessage());
            }
        });

        searchButton.setOnAction(e -> {
            String uuidStr = uuidField.getText();
            try {
                UUID uuid = UUID.fromString(uuidStr);
                Clothes found = store.findByUuid(uuid);
                if (found != null) {
                    resultArea.setText(found.toString());
                } else {
                    resultArea.setText("Об'єкт з таким UUID не знайдено.");
                }
            } catch (IllegalArgumentException ex) {
                resultArea.setText("Некоректний формат UUID!");
            }
        });

        updateShortList();

        Scene scene = new Scene(root, 700, 750);
        primaryStage.setTitle("Магазин одягу - JavaFX GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateShortList() {
        shortInfoList.clear();
        for (Clothes item : store.getClothesList()) {
            shortInfoList.add(item.getShortInfo());
        }
        listView.setItems(shortInfoList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}