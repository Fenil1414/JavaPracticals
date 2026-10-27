import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemServiceTest {

    @Test
    void addingItemIncreasesCount() {
        ItemService service = new ItemService();

        assertEquals(0, service.getCount());

        service.addItem(new Item("Pen"));

        assertEquals(1, service.getCount());
    }

    @Test
    void invalidActionThrowsException() {
        ItemService service = new ItemService();

        assertThrows(IllegalArgumentException.class, () -> {
            service.performAction("invalid");
        });
    }

    @Test
    void addingNullThrowsException() {
        ItemService service = new ItemService();

        assertThrows(IllegalArgumentException.class, () -> {
            service.addItem(null);
        });
    }
}