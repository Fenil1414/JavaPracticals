import java.util.regex.Pattern;

public class MiniBank {
    static class Account {
        private final String accountNumber;
        private String ownerName;
        private long balance;
        private boolean active = true;
        private static long counter;

        public Account(String ownerName, long balance) {
            accountNumber = String.format("AC%04d", ++counter);
            this.ownerName = ownerName;
            this.balance = balance;
        }

        public Account(String ownerName) {
            this(ownerName, 0);
        }

        public void deposit(long amount) {
            if (amount > 0) {
                balance += amount;
            }
        }

        public boolean withdraw(long amount) {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                return true;
            }
            return false;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public long getBalance() {
            return balance;
        }

        public boolean isActive() {
            return active;
        }
    }

    static class Validator {
        private static final Pattern MOBILE =
            Pattern.compile("^[6-9][0-9]{9}$");

        private static final Pattern EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

        private static final Pattern PAN =
            Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");

        private static final Pattern IFSC =
            Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

        private static boolean matches(Pattern p, String value) {
            return value != null && p.matcher(value).matches();
        }

        public static boolean isValidMobile(String s) {
            return matches(MOBILE, s);
        }

        public static boolean isValidEmail(String s) {
            return matches(EMAIL, s);
        }

        public static boolean isValidPan(String s) {
            return matches(PAN, s);
        }

        public static boolean isValidIfsc(String s) {
            return matches(IFSC, s);
        }
    }

    enum TransactionType {
        DEPOSIT, WITHDRAW, TRANSFER
    }

    record Command(TransactionType type, String accountNumber, long amount) {}

    static class CommandParser {
        public static Command parse(String line) {
            String[] parts = line.trim().split("\\s+");

            if (parts.length != 3) {
                throw new IllegalArgumentException(
                    "Use TYPE ACCOUNT AMOUNT"
                );
            }

            long amount = Long.parseLong(parts[2]);

            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive");
            }

            return new Command(
                TransactionType.valueOf(parts[0].toUpperCase()),
                parts[1],
                amount
            );
        }
    }

    static class StatementFormatter {
        public static String buildStatement(Account a) {
            return new StringBuilder()
                .append("Account: ").append(a.getAccountNumber())
                .append("\nOwner: ").append(a.getOwnerName())
                .append("\nBalance: ").append(a.getBalance())
                .append("\nActive: ").append(a.isActive())
                .toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(Validator.isValidMobile("9876543210"));
        System.out.println(Validator.isValidMobile("12345"));

        System.out.println(Validator.isValidEmail("abc@xyz.com"));
        System.out.println(Validator.isValidEmail("abc"));

        System.out.println(Validator.isValidPan("ABCDE1234F"));
        System.out.println(Validator.isValidPan("1234"));

        System.out.println(Validator.isValidIfsc("SBIN0001234"));
        System.out.println(Validator.isValidIfsc("INVALID"));

        Command c = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println(
            c.type() + " " + c.accountNumber() + " " + c.amount()
        );

        System.out.println(
            StatementFormatter.buildStatement(new Account("Fenil", 1000))
        );
    }
}