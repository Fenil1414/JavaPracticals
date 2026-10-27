
import java.util.Scanner;

public class ItemApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ItemService service = new ItemService();

        while (true) {
            System.out.println("\n1. Add Item");
            System.out.println("2. Show Count");
            System.out.println("3. Display Items");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1:
                        service.performAction("add");
                        System.out.print("Enter item name: ");
                        String name = sc.nextLine();
                        service.addItem(new Item(name));
                        System.out.println("Item added");
                        break;

                    case 2:
                        System.out.println("Count: " + service.count());
                        break;

                    case 3:
                        service.displayItems();
                        break;

                    case 4:
                        sc.close();
                        return;

                    default:
                        service.performAction("invalid");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
