import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
public class ItemService {
    private ArrayList<Item> items = new ArrayList<>();
    public void add(String name) {
        if (name == null || name.isBlank()) { throw new IllegalArgumentException("Name required"); }
        items.add(new Item(name));
    }
    public void save(Path file) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(file))) {
            out.writeObject(items);
        }
    }
    @SuppressWarnings("unchecked")
    public void load(Path file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(file))) {
            items = (ArrayList<Item>) in.readObject();
        }
    }
    public int count() { return items.size(); }
    public ArrayList<Item> all() { return new ArrayList<>(items); }
    public void performAction(String string) {
        throw new UnsupportedOperationException("Unimplemented method 'performAction'");
    }
    public void displayItems() {
        throw new UnsupportedOperationException("Unimplemented method 'displayItems'");
    }
    public void addItem(Item item) {
        throw new UnsupportedOperationException("Unimplemented method 'addItem'");
    }
}
