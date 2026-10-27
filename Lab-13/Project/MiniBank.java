import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

class Account {
    @Id
    private final String accountNumber;
    private final String ownerName;
    private long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }
}

class Repository<T> {
    private final HashMap<String, T> data = new HashMap<>();

    private String idOf(T entity) {
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
        data.put(idOf(entity), entity);
        return entity;
    }

    public synchronized T findById(String id) {
        return data.get(id);
    }

    public synchronized ArrayList<T> findAll() {
        return new ArrayList<>(data.values());
    }

    public synchronized boolean delete(String id) {
        return data.remove(id) != null;
    }

    @SafeVarargs
    public final synchronized void saveAll(T... items) {
        for (T item : items) save(item);
    }

    public static <N extends Number> double average(N[] values) {
        if (values.length == 0) return 0;

        double sum = 0;
        for (N value : values) sum += value.doubleValue();

        return sum / values.length;
    }
}

public class MiniBank {
    public static void main(String[] args) {
        Repository<Account> repository = new Repository<>();

        Account a = new Account("AC0001", "Fenil", 5000);
        Account b = new Account("AC0002", "Heer", 2000);
        Account c = new Account("AC0003", "Aman", 9000);

        repository.saveAll(a, b, c);

        System.out.println(repository.findById("AC0001"));

        for (Account account : repository.findAll()) {
            System.out.println(account);
        }

        Integer[] numbers = {10, 20, 30};
        System.out.println("Average: " + Repository.average(numbers));

        System.out.println("Deleted: " + repository.delete("AC0002"));
    }
}