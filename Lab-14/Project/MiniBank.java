import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Pattern;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }
}

class InvalidAmountException extends BankException {
    public InvalidAmountException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends BankException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

class Account {
    @Id
    private final String accountNumber;
    private final String ownerName;
    private long balance;

    private static int counter = 0;

    public Account(String ownerName, long balance) {
        this.accountNumber = String.format("AC%04d", ++counter);
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public void deposit(long amount) throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }
        balance += amount;
    }

    public boolean withdraw(long amount) throws BankException {
        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        balance -= amount;
        return true;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public long getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Balance: " + balance;
    }
}

class Validator {
    private static final Pattern MOBILE =
        Pattern.compile("^[6-9][0-9]{9}$");

    private static final Pattern EMAIL =
        Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE.matcher(mobile).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }
}

class Repository<T> {
    private final Map<String, T> data = new LinkedHashMap<>();

    private String getId(T entity) {
        try {
            for (Class<?> type = entity.getClass(); type != null;
                 type = type.getSuperclass()) {
                for (Field field : type.getDeclaredFields()) {
                    if (field.isAnnotationPresent(Id.class)) {
                        field.setAccessible(true);
                        return String.valueOf(field.get(entity));
                    }
                }
            }
            throw new IllegalArgumentException("No @Id field found");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized T save(T entity) {
        data.put(getId(entity), entity);
        return entity;
    }

    public synchronized T findById(String id) {
        return data.get(id);
    }

    public synchronized List<T> findAll() {
        return new ArrayList<>(data.values());
    }
}

public class MiniBank {
    private static final Scanner sc = new Scanner(System.in);
    private static final Repository<Account> accounts = new Repository<>();

    public static void main(String[] args) {
        System.out.println("=== MiniBank ===");

        while (true) {
            System.out.println("\n1. Open account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View accounts");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice");
                continue;
            }

            try {
                switch (choice) {
                    case 1 -> openAccount();
                    case 2 -> deposit();
                    case 3 -> withdraw();
                    case 4 -> viewAccounts();
                    case 5 -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice");
                }
            } catch (BankException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void openAccount() {
        System.out.print("Enter owner name: ");
        String name = sc.nextLine();

        System.out.print("Enter opening balance: ");
        try {
            long balance = Long.parseLong(sc.nextLine());

            if (balance < 0) {
                System.out.println("Balance cannot be negative");
                return;
            }

            Account account = new Account(name, balance);
            accounts.save(account);
            System.out.println("Account created: " + account);
        } catch (NumberFormatException e) {
            System.out.println("Invalid balance");
        }
    }

    private static void deposit() throws BankException {
        Account account = findAccount();
        if (account == null) return;

        System.out.print("Enter deposit amount: ");
        try {
            long amount = Long.parseLong(sc.nextLine());
            account.deposit(amount);
            System.out.println("Updated balance: " + account.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount");
        }
    }

    private static void withdraw() throws BankException {
        Account account = findAccount();
        if (account == null) return;

        System.out.print("Enter withdrawal amount: ");
        try {
            long amount = Long.parseLong(sc.nextLine());
            account.withdraw(amount);
            System.out.println("Updated balance: " + account.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount");
        }
    }

    private static Account findAccount() {
        System.out.print("Enter account number: ");
        String id = sc.nextLine();

        Account account = accounts.findById(id);

        if (account == null) {
            System.out.println("Account not found");
        }

        return account;
    }

    private static void viewAccounts() {
        List<Account> all = accounts.findAll();

        if (all.isEmpty()) {
            System.out.println("No accounts found");
            return;
        }

        for (Account account : all) {
            System.out.println(account);
        }
    }
}