import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class Account {
    private final String accountNumber;
    private long balance;
    private final Lock lock = new ReentrantLock();

    public Account(String accountNumber, long balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(long amount) {
        lock.lock();
        try {
            if (amount > 0) balance += amount;
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(long amount) {
        lock.lock();
        try {
            if (amount > 0 && amount <= balance) {
                balance -= amount;
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public long getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock();
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

class TransactionBuffer {
    private final BlockingQueue<String> transactions =
        new LinkedBlockingQueue<>();

    public void add(String transaction) {
        transactions.offer(transaction);
    }

    public String take() throws InterruptedException {
        return transactions.take();
    }
}

class TransactionProcessor {
    private final ExecutorService pool =
        Executors.newFixedThreadPool(2);

    public void submit(Runnable task) {
        pool.execute(task);
    }

    public void stop() throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(1, TimeUnit.MINUTES)) {
            pool.shutdownNow();
        }
    }
}

public class MiniBank {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account("AC0001", 1000);
        TransactionBuffer buffer = new TransactionBuffer();
        TransactionProcessor processor = new TransactionProcessor();

        buffer.add("Deposit 500");
        buffer.add("Withdraw 200");

        processor.submit(() -> account.deposit(500));
        processor.submit(() -> account.withdraw(200));

        processor.stop();

        System.out.println("Final balance: " + account.getBalance());
        System.out.println(buffer.take());
        System.out.println(buffer.take());
    }
}