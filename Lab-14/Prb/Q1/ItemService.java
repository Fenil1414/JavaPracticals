
import java.util.ArrayList;

public class ItemService {
    private ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Invalid item");
        }
        items.add(item);
    }

    public int getCount() {
        return items.size();
    }

    public void performAction(String action) {
        if (!action.equals("add") &&
            !action.equals("count") &&
            !action.equals("list")) {
            throw new IllegalArgumentException("Invalid action");
        }
    }

    public void displayItems() {
        for (Item item : items) {
            System.out.println(item.name());
        }
    }
}
