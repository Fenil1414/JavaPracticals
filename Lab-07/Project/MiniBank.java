import java.lang.annotation.*;
import java.lang.reflect.Field;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Positive {
    String message() default "must be > 0";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

abstract class Account {
    private static int counter = 0;

    @Id
    private final String accountNumber;

    @MaxLength(30)
    private String ownerName;

    @Positive
    private long balance;

    public Account(String name, long balance) {
        accountNumber = String.format("AC%04d", ++counter);
        ownerName = name;
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Account a)) return false;
        return accountNumber.equals(a.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber.hashCode();
    }
}

class SavingsAccount extends Account {
    public SavingsAccount(String name, long balance) {
        super(name, balance);
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && amount <= getBalance();
    }
}

class AnnotationValidator {
    public static String[] validate(Object obj) {
        java.util.ArrayList<String> errors = new java.util.ArrayList<>();

        for (Class<?> type = obj.getClass(); type != null;
             type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);

                    if (field.isAnnotationPresent(Positive.class)
                            && value instanceof Number n
                            && n.doubleValue() <= 0) {
                        errors.add(field.getName() + " "
                            + field.getAnnotation(Positive.class).message());
                    }

                    if (field.isAnnotationPresent(MaxLength.class)
                            && value instanceof String s
                            && s.length() > field.getAnnotation(MaxLength.class).value()) {
                        errors.add(field.getName() + " exceeds maximum length");
                    }
                } catch (IllegalAccessException e) {
                    errors.add("Could not validate " + field.getName());
                }
            }
        }

        return errors.toArray(new String[0]);
    }
}

public class MiniBank {
    public static void main(String[] args) {
        Account a = new SavingsAccount("Tej", -500);

        String[] errors = AnnotationValidator.validate(a);

        for (String error : errors) {
            System.out.println(error);
        }
    }
}