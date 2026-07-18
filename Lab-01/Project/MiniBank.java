import java.util.Scanner;

class MiniBank {

    record BankInfo(String name, String branch) {}

    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        EXIT
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BankInfo bankInfo = new BankInfo("MiniBank", "Petlad Branch");

        System.out.println("=================================");
        System.out.println("      " + bankInfo.name());
        System.out.println("      Branch: " + bankInfo.branch());
        System.out.println("=================================");

        while (true) {

            System.out.println("\n------ Main Menu ------");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            String message = switch (choice) {
                case 1 -> "Open Account - To be implemented in a later lab.";
                case 2 -> "Deposit - To be implemented in a later lab.";
                case 3 -> "Withdraw - To be implemented in a later lab.";
                case 4 -> "Transfer - To be implemented in a later lab.";
                case 5 -> "Thank you for using MiniBank. Goodbye!";
                default -> "Invalid choice! Please try again.";
            };

            System.out.println(message);

            if (choice == 5) {
                break;
            }
        }

        scanner.close();
    }
} 