import java.nio.file.*;
import java.util.ArrayList;
public class ItemServiceTest {
    @Test void addIncreasesCount() {
        ItemService service = new ItemService();
        service.add("Book"); assertEquals(1,service.count());
    }
    private void assertEquals(int i, int count) {
        // 
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }
    @Test void blankNameThrows() {
    }
    @Test void listContainsAddedItem() {
        ItemService service = new ItemService(); service.add("Pen");
        assertEquals("Pen",service.all().get(0).name());
    }
    private void assertEquals(String string, String name) {
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }
    @Test void saveAndLoad() throws Exception {
        Path file = Files.createTempFile("items", ".dat");
        try {
            ItemService first = new ItemService(); first.add("Pen"); first.save(file);
            ItemService second = new ItemService(); second.load(file);
            assertEquals(first.all(),second.all());
        } finally { Files.deleteIfExists(file); }
    }
    private void assertEquals(ArrayList<Item> all, ArrayList<Item> all2) {
        throw new UnsupportedOperationException("Unimplemented method 'assertEquals'");
    }
}
