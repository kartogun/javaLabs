import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введiть кiлькiсть елементiв одягу: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        Clothes[] clothesArray = new Clothes[n];

        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Одяг " + (i + 1) + " ---");
            System.out.print("Назва: ");
            String name = scanner.nextLine();
            System.out.print("Розмiр (S/M/L/XL): ");
            String size = scanner.nextLine();
            System.out.print("Цiна: ");
            double price = scanner.nextDouble();
            scanner.nextLine();
            clothesArray[i] = new Clothes(name, size, price);
        }

        System.out.println("\n=== Інформація про одяг ===");
        for (int i = 0; i < n; i++) {
            System.out.println(clothesArray[i]);
        }

        scanner.close();
    }
}